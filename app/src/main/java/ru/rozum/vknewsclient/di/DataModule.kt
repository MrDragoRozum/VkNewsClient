package ru.rozum.vknewsclient.di

import android.content.Context
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import dagger.Binds
import dagger.Module
import dagger.Provides
import ru.rozum.vknewsclient.data.network.ApiFactory
import ru.rozum.vknewsclient.data.network.ApiService
import ru.rozum.vknewsclient.data.repository.NewsFeedRepositoryImpl
import ru.rozum.vknewsclient.domain.repository.NewsFeedRepository

@Module
interface DataModule {

    @ApplicationScope
    @Binds
    fun bindRepository(impl: NewsFeedRepositoryImpl): NewsFeedRepository

    companion object {
        @ApplicationScope
        @Provides
        fun provideApiService(): ApiService = ApiFactory.apiService

        @ApplicationScope
        @Provides
        fun provideVKStorage(context: Context): VKPreferencesKeyValueStorage =
            VKPreferencesKeyValueStorage(context)
    }

}