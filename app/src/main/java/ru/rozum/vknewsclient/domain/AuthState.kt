package ru.rozum.vknewsclient.domain

sealed class AuthState {
    data object Initial : AuthState()
    data object Authorized : AuthState()
    data object NotAuthorized : AuthState()
}