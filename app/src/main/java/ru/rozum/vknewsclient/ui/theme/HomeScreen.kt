package ru.rozum.vknewsclient.ui.theme

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import ru.rozum.vknewsclient.MainViewModel
import ru.rozum.vknewsclient.domain.PostComment

@Composable
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
fun HomeScreen(
    viewModel: MainViewModel,
    paddingValues: PaddingValues
) {
    val feedPosts = viewModel.feedPosts.observeAsState(listOf())

    if (feedPosts.value.isNotEmpty()) {
        val comments = mutableListOf<PostComment>().apply {
            repeat(20) {
                add(
                    PostComment(id = it)
                )
            }
        }
        CommentScreen(
            modifier = Modifier.padding(paddingValues),
            feedPost = feedPosts.value[0],
            comments = comments
        )
    }


//    LazyColumn(
//        modifier = Modifier.padding(paddingValues),
//        contentPadding = PaddingValues(
//            top = 16.dp,
//            start = 8.dp,
//            end = 8.dp,
//            bottom = 16.dp
//        ),
//        verticalArrangement = Arrangement.spacedBy(8.dp)
//    ) {
//
//        itemsIndexed(items = feedPosts.value, key = { _, item -> item.id }) { index, feedPost ->
//            val dismissState = rememberDismissState()
//
//
//            if (dismissState.isDismissed(DismissDirection.StartToEnd)) {
//                viewModel.removePost(feedPost)
//            }
//
//            SwipeToDismiss(
//                modifier = Modifier.animateItemPlacement(),
//                directions = setOf(DismissDirection.StartToEnd),
//                state = dismissState,
//                background = {},
//                dismissContent = {
//                    PostCard(
//                        feedPost = feedPost,
//                        onViewsClickListener = { item ->
//                            viewModel.updateCount(index, item)
//                        },
//                        onShareClickListener = { item ->
//                            viewModel.updateCount(index, item)
//                        },
//                        onCommentClickListener = { item ->
//                            viewModel.updateCount(index, item)
//                        },
//                        onLikeClickListener = { item ->
//                            viewModel.updateCount(index, item)
//                        }
//                    )
//                })
//        }
//    }
}