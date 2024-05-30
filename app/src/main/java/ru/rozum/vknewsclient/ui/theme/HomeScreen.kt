package ru.rozum.vknewsclient.ui.theme

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.rozum.vknewsclient.MainViewModel
import ru.rozum.vknewsclient.domain.FeedPost

@Composable
fun HomeScreen(
    viewModel: MainViewModel,
    paddingValues: PaddingValues
) {
    val screenState = viewModel.stateScreen.observeAsState(HomeScreenState.Initial)

    when (val currentState = screenState.value) {
        is HomeScreenState.Comments -> CommentScreen(
            feedPost = currentState.feedPost,
            modifier = Modifier.padding(paddingValues),
            comments = currentState.comments
        )

        is HomeScreenState.Posts -> FeedPosts(paddingValues, currentState.posts, viewModel)
        HomeScreenState.Initial -> {}
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
private fun FeedPosts(
    paddingValues: PaddingValues,
    posts: List<FeedPost>,
    viewModel: MainViewModel
) {
    LazyColumn(
        modifier = Modifier.padding(paddingValues),
        contentPadding = PaddingValues(
            top = 16.dp,
            start = 8.dp,
            end = 8.dp,
            bottom = 16.dp
        ),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        itemsIndexed(items = posts, key = { _, item -> item.id }) { index, feedPost ->
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