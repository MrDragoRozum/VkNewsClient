package ru.rozum.vknewsclient.domain.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import ru.rozum.vknewsclient.domain.entity.AuthState
import ru.rozum.vknewsclient.domain.entity.FeedPost
import ru.rozum.vknewsclient.domain.entity.PostComment

interface NewsFeedRepository {

    fun getAuthStateFlow(): StateFlow<AuthState>
    fun getRecommendations(): StateFlow<List<FeedPost>>
    fun getComments(feedPost: FeedPost): StateFlow<List<PostComment>>
    suspend fun loadNextData()
    suspend fun checkAuthState()
    suspend fun deletePost(feedPost: FeedPost)
    suspend fun changeLikeStatus(feedPost: FeedPost)
}