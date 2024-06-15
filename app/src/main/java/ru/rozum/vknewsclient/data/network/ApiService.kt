package ru.rozum.vknewsclient.data.network

import retrofit2.http.GET
import retrofit2.http.Query
import ru.rozum.vknewsclient.data.model.LikesCountResponseDto
import ru.rozum.vknewsclient.data.model.NewsFeedResponseDto
import ru.rozum.vknewsclient.data.model.ResponseCommentsDto

interface ApiService {

    @GET("newsfeed.getRecommended?v=5.199")
    suspend fun loadRecommendations(@Query("access_token") token: String): NewsFeedResponseDto

    @GET("newsfeed.getRecommended?v=5.199")
    suspend fun loadRecommendations(
        @Query("access_token") token: String,
        @Query("start_from") startFrom: String
    ): NewsFeedResponseDto

    @GET("likes.add?v=5.1999&type=post")
    suspend fun addLike(
        @Query("access_token") token: String,
        @Query("owner_id") ownerId: Long,
        @Query("item_id") postId: Long
    ): LikesCountResponseDto

    @GET("likes.delete?v=5.1999&type=post")
    suspend fun deleteLike(
        @Query("access_token") token: String,
        @Query("owner_id") ownerId: Long,
        @Query("item_id") postId: Long
    ): LikesCountResponseDto

    @GET("newsfeed.ignoreItem?v=5.1999&type=wall")
    suspend fun ignorePost(
        @Query("access_token") token: String,
        @Query("item_id") itemId: Long,
        @Query("owner_id") ownerId: Long
    )

    @GET("wall.getComments?v=5.1999&fields=photo_100,first_name,last_name&extended=1")
    suspend fun loadComments(
        @Query("access_token") token: String,
        @Query("post_id") postId: Long,
        @Query("owner_id") ownerId: Long
    ): ResponseCommentsDto
}