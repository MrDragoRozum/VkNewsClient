package ru.rozum.vknewsclient.domain.ucecases

import ru.rozum.vknewsclient.domain.repository.NewsFeedRepository
import javax.inject.Inject

class CheckAuthStateUseCase @Inject constructor(
    private val repository: NewsFeedRepository
) {
    suspend operator fun invoke() = repository.checkAuthState()
}