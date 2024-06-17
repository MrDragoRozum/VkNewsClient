package ru.rozum.vknewsclient.presentation.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.rozum.vknewsclient.data.repository.NewsFeedRepositoryImpl
import ru.rozum.vknewsclient.domain.ucecases.CheckAuthStateUseCase
import ru.rozum.vknewsclient.domain.ucecases.GetAuthStateFlowUseCase

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = NewsFeedRepositoryImpl(application)
    private val getAuthStateFlowUseCase = GetAuthStateFlowUseCase(repository)
    private val checkAuthStateUseCase = CheckAuthStateUseCase(repository)

    val authState = getAuthStateFlowUseCase()

    fun performAuthResult() {
        viewModelScope.launch {
            checkAuthStateUseCase()
        }
    }
}