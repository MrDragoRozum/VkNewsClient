package ru.rozum.vknewsclient.presentation.comments

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.map
import ru.rozum.vknewsclient.data.repository.NewsFeedRepositoryImpl
import ru.rozum.vknewsclient.domain.entity.FeedPost
import ru.rozum.vknewsclient.domain.ucecases.GetCommentsUseCase

class CommentsViewModel(
    feedPost: FeedPost,
    application: Application
) : AndroidViewModel(application) {

    private val repository = NewsFeedRepositoryImpl(application)
    private val getCommentsScreenState = GetCommentsUseCase(repository)

    val screenState =
        getCommentsScreenState(feedPost).map {
            CommentsScreenState.Comments(
                feedPost = feedPost,
                comments = it
            ) as CommentsScreenState
        }
}