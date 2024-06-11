package ru.rozum.vknewsclient.presentation.main

sealed class AuthState {
    data object Initial : AuthState()
    data object Authorized : AuthState()
    data object NotAuthorized : AuthState()
}