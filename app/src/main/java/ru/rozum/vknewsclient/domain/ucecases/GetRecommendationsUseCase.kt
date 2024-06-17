package ru.rozum.vknewsclient.domain.ucecases

import kotlinx.coroutines.flow.StateFlow
import ru.rozum.vknewsclient.domain.repository.NewsFeedRepository
import ru.rozum.vknewsclient.domain.entity.FeedPost

class GetRecommendationsUseCase(
    private val repository: NewsFeedRepository
) {
    operator fun invoke(): StateFlow<List<FeedPost>> = repository.getRecommendations()
}