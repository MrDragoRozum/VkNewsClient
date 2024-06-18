package ru.rozum.vknewsclient.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.rozum.vknewsclient.di.ApplicationScope
import javax.inject.Inject
import javax.inject.Provider

class ViewModelFactory @Inject constructor(
    private val viewModelProviders: @JvmSuppressWildcards Map<Class<out ViewModel>, Provider<ViewModel>>
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        viewModelProviders.forEach {
            println("Текущие ViewModels в мапке ${it.value.get()::class.simpleName}")
        }
        println("Ссылка на ViewModelFactory: $this")
        println("Текущий ViewModel, который достали через modelClass (${modelClass.simpleName}) ${viewModelProviders[modelClass]?.get()?.javaClass?.simpleName}")
        return viewModelProviders[modelClass]?.get() as T
    }

}