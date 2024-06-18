package ru.rozum.vknewsclient.data.repository

import com.vk.api.sdk.VKPreferencesKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.stateIn
import ru.rozum.vknewsclient.data.model.toPostComments
import ru.rozum.vknewsclient.data.model.toPosts
import ru.rozum.vknewsclient.data.network.ApiService
import ru.rozum.vknewsclient.domain.entity.AuthState
import ru.rozum.vknewsclient.domain.entity.FeedPost
import ru.rozum.vknewsclient.domain.entity.PostComment
import ru.rozum.vknewsclient.domain.entity.StatisticItem
import ru.rozum.vknewsclient.domain.entity.StatisticType
import ru.rozum.vknewsclient.domain.repository.NewsFeedRepository
import ru.rozum.vknewsclient.extensions.mergeWith
import javax.inject.Inject

class NewsFeedRepositoryImpl @Inject constructor(
    private val storage: VKPreferencesKeyValueStorage,
    private val apiService: ApiService
) : NewsFeedRepository {

    private val token get() = VKAccessToken.restore(storage)
    private val coroutineScope = CoroutineScope(Dispatchers.Default)
    private val nextDataNeededEvents = MutableSharedFlow<Unit>(replay = 1)
    private val refreshedListFlow = MutableSharedFlow<List<FeedPost>>()
    private val checkAuthStateEvents = MutableSharedFlow<Unit>(replay = 1)
    private val accessToken: String
        get() {
            return token?.accessToken ?: throw IllegalArgumentException("Token is null")
        }

    private val _feedPosts = mutableListOf<FeedPost>()
    private var nextFrom: String? = null
    private val feedPosts: List<FeedPost>
        get() = _feedPosts.toList()

    private val loadedListFlow = flow {
        nextDataNeededEvents.emit(Unit)
        nextDataNeededEvents.collect {
            val startFrom = nextFrom
            if (startFrom == null && _feedPosts.isNotEmpty()) {
                emit(feedPosts)
                return@collect
            }
            val response = if (startFrom == null) {
                apiService.loadRecommendations(accessToken)
            } else {
                apiService.loadRecommendations(accessToken, startFrom)
            }
            nextFrom = response.newsFeedContent.nextFrom
            _feedPosts.addAll(response.toPosts())
            emit(feedPosts)
        }
    }.retry {
        delay(RETRY_TIMEOUT_MILLIS)
        true
    }

    private val authState: StateFlow<AuthState> = flow {
        checkAuthStateEvents.emit(Unit)
        checkAuthStateEvents.collect {
            val currentToken = token
            val loggedIn =  currentToken != null && currentToken.isValid
            val currentState = if (loggedIn) AuthState.Authorized else AuthState.NotAuthorized
            emit(currentState)
        }
    }.retry {
        delay(RETRY_TIMEOUT_MILLIS)
        true
    }.stateIn(
        scope = coroutineScope,
        started = SharingStarted.Lazily,
        initialValue = AuthState.Initial
    )

    override suspend fun checkAuthState() = checkAuthStateEvents.emit(Unit)

    override fun getAuthStateFlow(): StateFlow<AuthState> = authState

    override fun getRecommendations(): StateFlow<List<FeedPost>> = recommendations

    override suspend fun loadNextData() = nextDataNeededEvents.emit(Unit)

    override fun getComments(feedPost: FeedPost): StateFlow<List<PostComment>> = flow {
        val response = apiService.loadComments(
            token = accessToken,
            postId = feedPost.id,
            ownerId = feedPost.communityId
        )
        emit(response.toPostComments())
    }.retry {
        delay(RETRY_TIMEOUT_MILLIS)
        true
    }.stateIn(
        scope = coroutineScope,
        started = SharingStarted.Lazily,
        initialValue = listOf()
    )



    private val recommendations: StateFlow<List<FeedPost>> = loadedListFlow
        .mergeWith(refreshedListFlow)
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.Lazily,
            initialValue = feedPosts
        )

    override suspend fun changeLikeStatus(feedPost: FeedPost) {
        val response = if (feedPost.isLiked) {
            apiService.deleteLike(
                token = accessToken,
                ownerId = feedPost.communityId,
                postId = feedPost.id
            )
        } else {
            apiService.addLike(
                token = accessToken,
                ownerId = feedPost.communityId,
                postId = feedPost.id
            )
        }
        val updatedCountLikes = response.likes.count
        val newStatistics = feedPost.statistics.toMutableList().apply {
            removeIf {
                it.type == StatisticType.LIKES
            }
            add(StatisticItem(StatisticType.LIKES, updatedCountLikes))
        }
        val updatedFeedPost = feedPost.copy(
            isLiked = !feedPost.isLiked,
            statistics = newStatistics
        )
        val postIndex = _feedPosts.indexOf(feedPost)
        _feedPosts[postIndex] = updatedFeedPost
        refreshedListFlow.emit(feedPosts)
    }

    override suspend fun deletePost(feedPost: FeedPost) {
        apiService.ignorePost(
            token = accessToken,
            ownerId = feedPost.communityId,
            itemId = feedPost.id
        )
        _feedPosts.remove(feedPost)
        refreshedListFlow.emit(feedPosts)
    }

    companion object {
        private const val RETRY_TIMEOUT_MILLIS = 3_000L
    }
}