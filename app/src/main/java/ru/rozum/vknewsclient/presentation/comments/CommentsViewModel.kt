package ru.rozum.vknewsclient.presentation.comments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.rozum.vknewsclient.domain.FeedPost
import ru.rozum.vknewsclient.domain.PostComment

class CommentsViewModel(feedPost: FeedPost) : ViewModel() {

    private val _screenState = MutableLiveData<CommentsScreenState>(CommentsScreenState.Initial)
    val screenState: LiveData<CommentsScreenState> = _screenState

    init {
        loadComments(feedPost)
    }

    private fun loadComments(feedPost: FeedPost) {
        val comments = mutableListOf<PostComment>().apply {
            repeat(20) {
                add(
                    PostComment(id = it)
                )
            }
        }
        _screenState.value = CommentsScreenState.Comments(feedPost, comments)
    }
}