package ru.rozum.vknewsclient.data.repository

import android.app.Application
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken
import ru.rozum.vknewsclient.data.model.toPosts
import ru.rozum.vknewsclient.data.network.ApiFactory
import ru.rozum.vknewsclient.domain.FeedPost
import ru.rozum.vknewsclient.domain.StatisticItem
import ru.rozum.vknewsclient.domain.StatisticType

class NewsFeedRepository(application: Application) {

    private val storage = VKPreferencesKeyValueStorage(application)
    private val token = VKAccessToken.restore(storage)

    private val accessToken: String
        get() {
            return token?.accessToken ?: throw IllegalArgumentException("Token is null")
        }

    private val apiService = ApiFactory.apiService
    private var _feedPosts = mutableListOf<FeedPost>()
    val feedPosts: List<FeedPost>
        get() = _feedPosts.toList()

    suspend fun loadRecommendations(): List<FeedPost> {
        val response = apiService.loadRecommendations(accessToken)
        _feedPosts.addAll(response.toPosts())
        return feedPosts
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
    }

}