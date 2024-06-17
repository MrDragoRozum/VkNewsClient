package ru.rozum.vknewsclient.domain.ucecases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import ru.rozum.vknewsclient.domain.entity.FeedPost
import ru.rozum.vknewsclient.domain.entity.PostComment
import ru.rozum.vknewsclient.domain.repository.NewsFeedRepository

class GetCommentsUseCase(
    private val repository: NewsFeedRepository
) {
    operator fun invoke(feedPost: FeedPost): StateFlow<List<PostComment>> =
        repository.getComments(feedPost)
}