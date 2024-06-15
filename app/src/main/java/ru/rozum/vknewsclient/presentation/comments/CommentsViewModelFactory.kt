package ru.rozum.vknewsclient.presentation.comments

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.rozum.vknewsclient.domain.FeedPost

@Suppress("UNCHECKED_CAST")
class CommentsViewModelFactory(
    private val feedPost: FeedPost,
    private val application: Application
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        CommentsViewModel(feedPost, application) as T
}