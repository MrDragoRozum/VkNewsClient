package ru.rozum.vknewsclient

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.rozum.vknewsclient.domain.FeedPost
import ru.rozum.vknewsclient.domain.PostComment
import ru.rozum.vknewsclient.ui.theme.CommentsScreenState

class CommentsViewModel : ViewModel() {

    private val _screenState = MutableLiveData<CommentsScreenState>(CommentsScreenState.Initial)
    val screenState: LiveData<CommentsScreenState> = _screenState

    init {
        showComment(FeedPost())
    }

    private fun comments() = mutableListOf<PostComment>().apply {
        repeat(20) {
            add(
                PostComment(id = it)
            )
        }
    }

    fun showComment(feedPost: FeedPost) {
        _screenState.value = CommentsScreenState.Comments(feedPost, comments())
    }
}