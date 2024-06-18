package ru.rozum.vknewsclient.presentation

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import ru.rozum.vknewsclient.di.ApplicationComponent
import ru.rozum.vknewsclient.di.DaggerApplicationComponent

class NewsFeedApplication : Application() {
    val component: ApplicationComponent by lazy {
        DaggerApplicationComponent.factory().create(
            context = this
        )
    }
}

val applicationComponent: ApplicationComponent
    @Composable get() {
        println("ApplicationComponent")
        return (LocalContext.current.applicationContext as NewsFeedApplication).component
    }