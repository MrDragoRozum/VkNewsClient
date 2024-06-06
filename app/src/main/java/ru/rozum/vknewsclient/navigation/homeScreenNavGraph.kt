package ru.rozum.vknewsclient.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import ru.rozum.vknewsclient.domain.FeedPost

fun NavGraphBuilder.homeScreenNavGraph(
    newsScreenContent: @Composable () -> Unit,
    commentsScreenContent: @Composable (FeedPost) -> Unit
) {
    navigation(
        startDestination = Screen.NewsFeed.route,
        route = Screen.Home.route
    ) {
        composable(Screen.NewsFeed.route) {
            newsScreenContent()
        }

        composable(Screen.Comments.route) {
            val feedPostId = it.arguments?.getInt(Screen.ROUTE_COMMENTS) ?: 0
            commentsScreenContent(FeedPost(id = feedPostId))
        }
    }
}