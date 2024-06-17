package ru.rozum.vknewsclient.presentation.comments

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.map
import ru.rozum.vknewsclient.data.repository.NewsFeedRepository
import ru.rozum.vknewsclient.domain.FeedPost

class CommentsViewModel(
    feedPost: FeedPost,
    application: Application
) : AndroidViewModel(application) {

    private val repository = NewsFeedRepository(application)

    val screenState =
        repository.getComments(feedPost).map {
            CommentsScreenState.Comments(
                feedPost = feedPost,
                comments = it
            ) as CommentsScreenState
        }
}