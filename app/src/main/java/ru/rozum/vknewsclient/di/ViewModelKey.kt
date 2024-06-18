package ru.rozum.vknewsclient.di

import androidx.lifecycle.ViewModel
import dagger.MapKey
import kotlin.reflect.KClass

@MapKey
@Retention
annotation class ViewModelKey(val viewModel: KClass<out ViewModel>)