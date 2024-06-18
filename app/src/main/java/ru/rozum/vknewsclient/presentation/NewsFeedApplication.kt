package ru.rozum.vknewsclient.presentation

import android.app.Application
import ru.rozum.vknewsclient.di.ApplicationComponent
import ru.rozum.vknewsclient.di.DaggerApplicationComponent

class NewsFeedApplication : Application() {
    val component: ApplicationComponent by lazy {
        DaggerApplicationComponent.factory().create(
            context = this
        )
    }
}