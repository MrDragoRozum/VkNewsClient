package ru.rozum.vknewsclient.di

import dagger.BindsInstance
import dagger.Subcomponent
import ru.rozum.vknewsclient.domain.entity.FeedPost
import ru.rozum.vknewsclient.presentation.ViewModelFactory

@Subcomponent(modules = [CommentsViewModelModule::class])
interface CommentsScreenComponent {

    fun getViewModelFactory(): ViewModelFactory

    @Subcomponent.Factory
    interface Factory {
        fun create(
            @BindsInstance feedPost: FeedPost
        ): CommentsScreenComponent
    }
}