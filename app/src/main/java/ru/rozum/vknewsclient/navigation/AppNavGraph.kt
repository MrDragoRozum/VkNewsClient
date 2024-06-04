package ru.rozum.vknewsclient.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun AppNavGraph(
    navHostController: NavHostController,
    newsScreenContent: @Composable () -> Unit,
    favouriteScreenContent: @Composable () -> Unit,
    profileScreenContent: @Composable () -> Unit,
    commentsScreenContent: @Composable () -> Unit
) {

    NavHost(navController = navHostController, startDestination = Screen.Home.route) {

        homeScreenNavGraph(
            newsScreenContent = newsScreenContent,
            commentsScreenContent = commentsScreenContent
        )

        composable(Screen.Profile.route) {
            profileScreenContent()
        }

        composable(Screen.Favourite.route) {
            favouriteScreenContent()
        }
    }
}

