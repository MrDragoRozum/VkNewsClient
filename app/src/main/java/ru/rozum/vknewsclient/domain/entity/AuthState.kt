package ru.rozum.vknewsclient.domain.entity

sealed class AuthState {
    data object Initial : AuthState()
    data object Authorized : AuthState()
    data object NotAuthorized : AuthState()
}