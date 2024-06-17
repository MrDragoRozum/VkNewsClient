package ru.rozum.vknewsclient.presentation.comments

import ru.rozum.vknewsclient.domain.entity.FeedPost
import ru.rozum.vknewsclient.domain.entity.PostComment

sealed class CommentsScreenState {
    data object Initial : CommentsScreenState()
    data class Comments(
        val feedPost: FeedPost,
        val comments: List<PostComment>
    ) : CommentsScreenState()
}