package ru.rozum.vknewsclient.data.repository

import android.app.Application
import android.util.Log
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.rozum.vknewsclient.data.model.toPostComments
import ru.rozum.vknewsclient.data.model.toPosts
import ru.rozum.vknewsclient.data.network.ApiFactory
import ru.rozum.vknewsclient.domain.FeedPost
import ru.rozum.vknewsclient.domain.PostComment
import ru.rozum.vknewsclient.domain.StatisticItem
import ru.rozum.vknewsclient.domain.StatisticType
import ru.rozum.vknewsclient.extensions.mergeWith

class NewsFeedRepository(application: Application) {

    private val storage = VKPreferencesKeyValueStorage(application)
    private val token = VKAccessToken.restore(storage)
    private val coroutineScope = CoroutineScope(Dispatchers.Default)
    private val nextDataNeededEvents = MutableSharedFlow<Unit>(replay = 1)
    private val refreshedListFlow = MutableSharedFlow<List<FeedPost>>()
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
    }

    private val accessToken: String
        get() {
            return token?.accessToken ?: throw IllegalArgumentException("Token is null")
        }

    private val apiService = ApiFactory.apiService
    private val _feedPosts = mutableListOf<FeedPost>()
    private var nextFrom: String? = null

    private val feedPosts: List<FeedPost>
        get() = _feedPosts.toList()

    val recommendations: StateFlow<List<FeedPost>> = loadedListFlow
        .mergeWith(refreshedListFlow)
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.Lazily,
            initialValue = feedPosts
        )

    suspend fun loadNextRecommendations() {
        Log.d("NewsFeedRepository", "Я вызвался в loadNextRecommendations")

        nextDataNeededEvents.emit(Unit)
    }

    suspend fun changeLikeStatus(feedPost: FeedPost) {
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

    suspend fun deletePost(feedPost: FeedPost) {
        apiService.ignorePost(
            token = accessToken,
            ownerId = feedPost.communityId,
            itemId = feedPost.id
        )
        _feedPosts.remove(feedPost)
        refreshedListFlow.emit(feedPosts)
    }

    suspend fun loadComments(feedPost: FeedPost): List<PostComment> {
        val response = apiService.loadComments(
            token = accessToken,
            postId = feedPost.id,
            ownerId = feedPost.communityId
        )

        return response.toPostComments()
    }
}