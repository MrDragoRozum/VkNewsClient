package ru.rozum.vknewsclient.domain.ucecases

import ru.rozum.vknewsclient.domain.entity.FeedPost
import ru.rozum.vknewsclient.domain.repository.NewsFeedRepository

class DeletePostUseCase(
    private val repository: NewsFeedRepository
) {
    suspend operator fun invoke(feedPost: FeedPost) = repository.deletePost(feedPost)
}