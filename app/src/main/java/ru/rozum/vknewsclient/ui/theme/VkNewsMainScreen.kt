package ru.rozum.vknewsclient.ui.theme

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.rozum.vknewsclient.MainViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(viewModel: MainViewModel) {
    val feedPosts = viewModel.feedPosts.observeAsState(listOf())

    Scaffold(
        bottomBar = {
            NavigationBar {

                val selectedItemPosition = rememberSaveable {
                    mutableIntStateOf(0)
                }

                listOf(
                    NavigationItem.Home,
                    NavigationItem.Favourite,
                    NavigationItem.Profile
                ).forEachIndexed { index, it ->

                    NavigationBarItem(
                        selected = index == selectedItemPosition.intValue,
                        onClick = { selectedItemPosition.intValue = index },
                        icon = { Icon(it.icon, contentDescription = null) },
                        label = { Text(text = stringResource(id = it.titleResId)) },
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
    ) {


        LazyColumn(
            modifier = Modifier.padding(it),
            contentPadding = PaddingValues(
                top = 16.dp,
                start = 8.dp,
                end = 8.dp,
                bottom = 16.dp
            ),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            itemsIndexed(items = feedPosts.value, key = { _, item -> item.id }) { index, feedPost ->
                val dismissState = rememberDismissState()


                if (dismissState.isDismissed(DismissDirection.StartToEnd)) {
                    viewModel.removePost(feedPost)
                }

                SwipeToDismiss(
                    modifier = Modifier.animateItemPlacement(),
                    directions = setOf(DismissDirection.StartToEnd),
                    state = dismissState,
                    background = {},
                    dismissContent = {
                        PostCard(
                            feedPost = feedPost,
                            onViewsClickListener = { item ->
                                viewModel.updateCount(index, item)
                            },
                            onShareClickListener = { item ->
                                viewModel.updateCount(index, item)
                            },
                            onCommentClickListener = { item ->
                                viewModel.updateCount(index, item)
                            },
                            onLikeClickListener = { item ->
                                viewModel.updateCount(index, item)
                            }
                        )
                    })
            }
        }
    }
}