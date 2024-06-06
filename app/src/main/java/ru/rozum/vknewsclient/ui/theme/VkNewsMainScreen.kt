package ru.rozum.vknewsclient.ui.theme

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import ru.rozum.vknewsclient.domain.FeedPost
import ru.rozum.vknewsclient.navigation.AppNavGraph
import ru.rozum.vknewsclient.navigation.Screen
import ru.rozum.vknewsclient.navigation.rememberNavigationState

@Composable
fun MainScreen() {
    val navigationState = rememberNavigationState()
    val state: MutableState<FeedPost?> = remember {
        mutableStateOf(null)
    }

    Scaffold(
        contentColor = MaterialTheme.colorScheme.primary,

        bottomBar = {
            NavigationBar(
                containerColor = Color.Unspecified
            ) {
                val navBackStackEntry by navigationState.navHostController.currentBackStackEntryAsState()

                listOf(
                    NavigationItem.Home,
                    NavigationItem.Favourite,
                    NavigationItem.Profile
                ).forEach { item ->

                    val selected = navBackStackEntry?.destination?.hierarchy?.any {
                        it.route == item.screen.route
                    } ?: false

                    NavigationBarItem(
                        selected = selected,
                        onClick = {
                            navigationState.navigateTo(item.screen.route)
                        },
                        icon = { Icon(item.icon, contentDescription = null) },
                        label = { Text(text = stringResource(id = item.titleResId)) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                            selectedTextColor = MaterialTheme.colorScheme.onPrimary,
                            unselectedIconColor = MaterialTheme.colorScheme.onSecondary,
                            unselectedTextColor = MaterialTheme.colorScheme.onSecondary,
                            indicatorColor = MaterialTheme.colorScheme.primary // TODO: поменять цвет
                        )
                    )
                }
            }
        }
    ) { paddingValues ->


        AppNavGraph(
            navHostController = navigationState.navHostController,
            newsScreenContent = {
                    HomeScreen(
                        paddingValues = paddingValues,
                        onCommentClickListener = {
                            state.value = it
                            navigationState.navigateToComment()
                        }
                    )
            },
            commentsScreenContent = {
                CommentScreen(
                    modifier = Modifier.padding(paddingValues),
                    feedPost = state.value!!,
                    onBackPressed =  {
                        navigationState.navHostController.popBackStack()
                    }
                )
            },
            favouriteScreenContent = { TextCounter(name = "Favourite") },
            profileScreenContent = { TextCounter(name = "Profile") })
    }
}

@Composable
private fun TextCounter(name: String) {
    var count by rememberSaveable {
        mutableIntStateOf(0)
    }

    Text(
        modifier = Modifier.clickable { count++ },
        text = "$name Count: $count",
        color = MaterialTheme.colorScheme.onPrimary
    )
}

