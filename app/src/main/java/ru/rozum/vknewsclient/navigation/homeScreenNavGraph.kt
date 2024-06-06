package ru.rozum.vknewsclient.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import ru.rozum.vknewsclient.domain.FeedPost

fun NavGraphBuilder.homeScreenNavGraph(
    newsScreenContent: @Composable () -> Unit,
    commentsScreenContent: @Composable (FeedPost, String) -> Unit
) {
    navigation(
        startDestination = Screen.NewsFeed.route,
        route = Screen.Home.route
    ) {
        composable(Screen.NewsFeed.route) {
            newsScreenContent()
        }

        composable(
            Screen.Comments.route,
            arguments = listOf(navArgument(Screen.KEY_FEED_POST_ID) {
                type = NavType.IntType
            }, navArgument(Screen.KEY_CONTENT_TEXT) {
                type = NavType.StringType
            })
        ) {
            val feedPostId = it.arguments?.getInt(Screen.KEY_FEED_POST_ID) ?: 0
            val contentText = it.arguments?.getString(Screen.KEY_CONTENT_TEXT) ?: ""
            commentsScreenContent(FeedPost(id = feedPostId), contentText)
        }
    }
}