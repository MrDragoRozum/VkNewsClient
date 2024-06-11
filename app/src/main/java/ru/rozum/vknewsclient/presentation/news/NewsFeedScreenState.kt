package ru.rozum.vknewsclient.presentation.news

import ru.rozum.vknewsclient.domain.FeedPost

sealed class NewsFeedScreenState {
    data object Initial : NewsFeedScreenState()
    data class Posts(val posts: List<FeedPost>) : NewsFeedScreenState()
}