package ru.rozum.vknewsclient.domain.ucecases

import kotlinx.coroutines.flow.StateFlow
import ru.rozum.vknewsclient.domain.entity.AuthState
import ru.rozum.vknewsclient.domain.repository.NewsFeedRepository

class GetAuthStateFlowUseCase(
    private val repository: NewsFeedRepository
) {
    operator fun invoke(): StateFlow<AuthState> = repository.getAuthStateFlow()
}