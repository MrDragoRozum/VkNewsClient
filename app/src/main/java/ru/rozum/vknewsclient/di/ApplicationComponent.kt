package ru.rozum.vknewsclient.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.rozum.vknewsclient.domain.entity.FeedPost
import ru.rozum.vknewsclient.presentation.main.MainActivity

@ApplicationScope
@Component(modules = [DataModule::class, ViewModelModule::class])
interface ApplicationComponent {

    fun inject(activity: MainActivity)

    fun getCommentsScreenComponentFactory(): CommentsScreenComponent.Factory

    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance context: Context
        ): ApplicationComponent
    }
}