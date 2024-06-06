package ru.rozum.vknewsclient.navigation

import ru.rozum.vknewsclient.domain.FeedPost

sealed class Screen(
    val route: String
) {
    data object NewsFeed : Screen(ROUTE_NEWS_FEED)
    data object Favourite : Screen(ROUTE_FAVOURITE)
    data object Profile : Screen(ROUTE_PROFILE)
    data object Home : Screen(ROUTE_HOME)
    data object Comments : Screen(ROUTE_COMMENTS) {

        private const val ROUTE_FOR_ARGS = "comments"

        fun getRouteWithArgs(feedPost: FeedPost): String = "$ROUTE_FOR_ARGS/${feedPost.id}"
    }

    companion object {
        const val ROUTE_NEWS_FEED = "news_feed"
        const val ROUTE_FAVOURITE = "favourite"
        const val ROUTE_PROFILE = "profile"
        const val ROUTE_HOME = "home"
        const val ROUTE_COMMENTS = "comments/{feed_post_id}"
    }
}