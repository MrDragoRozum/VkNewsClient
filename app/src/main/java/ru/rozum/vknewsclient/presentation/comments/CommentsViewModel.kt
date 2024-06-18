package ru.rozum.vknewsclient.presentation.comments

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.map
import ru.rozum.vknewsclient.domain.entity.FeedPost
import ru.rozum.vknewsclient.domain.ucecases.GetCommentsUseCase
import javax.inject.Inject

class CommentsViewModel @Inject constructor(
    private val feedPost: FeedPost,
    getCommentsScreenState: GetCommentsUseCase
) : ViewModel() {

    val screenState =
        getCommentsScreenState(feedPost).map {
            CommentsScreenState.Comments(
                feedPost = feedPost,
                comments = it
            ) as CommentsScreenState
        }
}