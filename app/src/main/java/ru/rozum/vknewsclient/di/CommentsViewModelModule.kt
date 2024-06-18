package ru.rozum.vknewsclient.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.rozum.vknewsclient.presentation.comments.CommentsViewModel

@Module
interface CommentsViewModelModule {

    @ViewModelKey(viewModel = CommentsViewModel::class)
    @Binds
    @IntoMap
    fun bindCommentsViewModel(viewModel: CommentsViewModel): ViewModel
}