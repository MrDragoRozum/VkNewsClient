package ru.rozum.vknewsclient.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.rozum.vknewsclient.presentation.comments.CommentsViewModel
import ru.rozum.vknewsclient.presentation.main.MainViewModel
import ru.rozum.vknewsclient.presentation.news.NewsFeedViewModel

@Module
interface ViewModelModule {

    @ViewModelKey(viewModel = NewsFeedViewModel::class)
    @Binds
    @IntoMap
    fun bindNewsFeedViewModel(viewModel: NewsFeedViewModel): ViewModel

    @ViewModelKey(viewModel = MainViewModel::class)
    @Binds
    @IntoMap
    fun bindMainViewModel(viewModel: MainViewModel): ViewModel
}