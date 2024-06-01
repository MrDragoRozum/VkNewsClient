package ru.rozum.vknewsclient.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.rozum.vknewsclient.CommentsViewModel
import ru.rozum.vknewsclient.domain.FeedPost

class CommentsViewModelFactory(
    private val feedPost: FeedPost
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = CommentsViewModel(feedPost) as T
}