package ru.rozum.vknewsclient.presentation.comments

import ru.rozum.vknewsclient.domain.FeedPost
import ru.rozum.vknewsclient.domain.PostComment

sealed class CommentsScreenState {
    data object Initial : CommentsScreenState()
    data class Comments(
        val feedPost: FeedPost,
        val comments: List<PostComment>
    ) : CommentsScreenState()
}