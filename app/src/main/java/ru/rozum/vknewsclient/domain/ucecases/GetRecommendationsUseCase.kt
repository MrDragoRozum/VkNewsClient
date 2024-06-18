package ru.rozum.vknewsclient.domain.ucecases

import kotlinx.coroutines.flow.StateFlow
import ru.rozum.vknewsclient.domain.repository.NewsFeedRepository
import ru.rozum.vknewsclient.domain.entity.FeedPost
import javax.inject.Inject

class GetRecommendationsUseCase @Inject constructor(
    private val repository: NewsFeedRepository
) {
    operator fun invoke(): StateFlow<List<FeedPost>> = repository.getRecommendations()
}