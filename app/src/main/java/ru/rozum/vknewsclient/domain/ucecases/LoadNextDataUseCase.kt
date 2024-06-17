package ru.rozum.vknewsclient.domain.ucecases

import ru.rozum.vknewsclient.domain.repository.NewsFeedRepository

class LoadNextDataUseCase(
    private val repository: NewsFeedRepository
) {
    suspend operator fun invoke() = repository.loadNextData()
}