package ru.rozum.vknewsclient.domain.ucecases

import ru.rozum.vknewsclient.domain.repository.NewsFeedRepository

class CheckAuthStateUseCase(
    private val repository: NewsFeedRepository
) {
    suspend operator fun invoke() = repository.checkAuthState()
}