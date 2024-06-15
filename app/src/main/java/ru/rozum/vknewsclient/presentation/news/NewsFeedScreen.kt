package ru.rozum.vknewsclient.presentation.news

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sumin.vknewsclient.ui.theme.DarkBlue
import ru.rozum.vknewsclient.domain.FeedPost

@Composable
fun NewsFeedScreen(
    paddingValues: PaddingValues,
    onCommentClickListener: (FeedPost) -> Unit
) {

    val viewModel: NewsFeedViewModel = viewModel()

    val screenState = viewModel.stateScreen.observeAsState(NewsFeedScreenState.Initial)

    when (val currentState = screenState.value) {

        is NewsFeedScreenState.Posts -> FeedPosts(
            paddingValues = paddingValues,
            posts = currentState.posts,
            viewModel = viewModel,
            onCommentClickListener = onCommentClickListener,
            nextDataIsLoading = currentState.nextDataIsLoading
        )

        NewsFeedScreenState.Initial -> {}
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
private fun FeedPosts(
    paddingValues: PaddingValues,
    posts: List<FeedPost>,
    viewModel: NewsFeedViewModel,
    onCommentClickListener: (FeedPost) -> Unit,
    nextDataIsLoading: Boolean
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
                        onCommentClickListener = {
                            onCommentClickListener(feedPost)
                        },
                        onLikeClickListener = { _ ->
                            viewModel.changeLikeStatus(feedPost)
                        }
                    )
                })
        }

        item {
            if (nextDataIsLoading) {
                Box(
                    Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = DarkBlue)
                }
            } else {
                SideEffect {
                    viewModel.loadNextRecommendations()
                }
            }
        }
    }
}