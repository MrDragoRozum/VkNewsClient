package ru.rozum.vknewsclient.presentation

import android.app.Application
import ru.rozum.vknewsclient.di.ApplicationComponent
import ru.rozum.vknewsclient.di.DaggerApplicationComponent
import ru.rozum.vknewsclient.domain.entity.FeedPost

class NewsFeedApplication : Application() {
    val component: ApplicationComponent by lazy {
        DaggerApplicationComponent.factory().create(
            context = this,
            feedPost = FeedPost(
                id = 0,
                communityName = "1",
                communityId = 1,
                publicationDate = "1",
                communityImageUrl = "",
                contentImageUrl = "",
                contentText = "",
                statistics = listOf(),
                isLiked = false
            )
        )
    }
}