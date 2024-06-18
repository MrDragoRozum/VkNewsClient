package ru.rozum.vknewsclient.presentation.comments

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import ru.rozum.vknewsclient.R
import ru.rozum.vknewsclient.domain.entity.FeedPost
import ru.rozum.vknewsclient.domain.entity.PostComment
import ru.rozum.vknewsclient.presentation.applicationComponent

@Composable
fun CommentScreen(
    modifier: Modifier,
    feedPost: FeedPost,
    onBackPressed: () -> Unit
) {
    val component = applicationComponent
        .getCommentsScreenComponentFactory()
        .create(feedPost)

    val viewModel: CommentsViewModel = viewModel(factory = component.getViewModelFactory())
    val state = viewModel.screenState.collectAsState(CommentsScreenState.Initial)
    CommentsScreenContent(
        state = state,
        modifier = modifier,
        onBackPressed = onBackPressed
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun CommentsScreenContent(
    state: State<CommentsScreenState>,
    modifier: Modifier,
    onBackPressed: () -> Unit
) {
    val currentState = state.value

    if (currentState is CommentsScreenState.Comments) {
        Scaffold(
            modifier = modifier,
            topBar = {
                CenterAlignedTopAppBar(
                    modifier = Modifier.shadow(elevation = 3.dp),
                    title = {
                        Text(
                            text = stringResource(R.string.comments_title),
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontSize = 18.sp
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary),
                    navigationIcon = {
                        IconButton(onClick = { onBackPressed() }) {
                            Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
                        }
                    }
                )
            }
        ) { paddingValues ->
            LazyColumn(
                modifier = Modifier.padding(paddingValues),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(18.dp)
            ) {
                items(items = currentState.comments, key = { it.id }) { comment ->
                    CommentItem(postComment = comment)
                }
            }
        }
    }
}

@Composable
fun CommentItem(postComment: PostComment) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {

        AsyncImage(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape),
            model = postComment.authorAvatarUrl,
            contentDescription = null
        )

        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(
                text = postComment.authorName,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = postComment.commentText, color = MaterialTheme.colorScheme.onPrimary)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = postComment.publicationDate,
                color = MaterialTheme.colorScheme.onSecondary,
                fontSize = 12.sp
            )
        }
    }
}