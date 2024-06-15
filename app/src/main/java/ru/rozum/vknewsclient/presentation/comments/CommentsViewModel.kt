package ru.rozum.vknewsclient.presentation.comments

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.rozum.vknewsclient.data.repository.NewsFeedRepository
import ru.rozum.vknewsclient.domain.FeedPost
import ru.rozum.vknewsclient.domain.PostComment

class CommentsViewModel(
    feedPost: FeedPost,
    application: Application
) : AndroidViewModel(application) {

    private val _screenState = MutableLiveData<CommentsScreenState>(CommentsScreenState.Initial)
    val screenState: LiveData<CommentsScreenState> = _screenState

    private val repository = NewsFeedRepository(application)

    init {
        loadComments(feedPost)
    }

    private fun loadComments(feedPost: FeedPost) {
        viewModelScope.launch {
            val comments = repository.loadComments(feedPost)
            _screenState.value = CommentsScreenState.Comments(feedPost, comments)
        }
    }
}