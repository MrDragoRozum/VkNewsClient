package ru.rozum.vknewsclient.domain.ucecases

import kotlinx.coroutines.flow.StateFlow
import ru.rozum.vknewsclient.domain.entity.AuthState
import ru.rozum.vknewsclient.domain.repository.NewsFeedRepository
import javax.inject.Inject

class GetAuthStateFlowUseCase @Inject constructor(
    private val repository: NewsFeedRepository
) {
    operator fun invoke(): StateFlow<AuthState> = repository.getAuthStateFlow()
}