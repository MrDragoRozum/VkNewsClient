package ru.rozum.vknewsclient.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.rozum.vknewsclient.R

@Composable
fun PostCard() {
    Card(
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 1.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        shape = RoundedCornerShape(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            PostHeader()

            Spacer(modifier = Modifier.height(8.dp))
            Text(text = stringResource(R.string.template_text))
            Spacer(modifier = Modifier.height(8.dp))

            Image(
                painter = painterResource(id = R.drawable.post_content_image),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(8.dp))
            Statistics()
        }
    }
}

@Composable
private fun PostHeader() {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.post_comunity_thumbnail),
            contentDescription = null,
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "Типичный программист",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Text(
                text = "14:00",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSecondary
            )
        }

        Icon(
            imageVector = Icons.Rounded.MoreVert,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSecondary
        )
    }
}

@Composable
private fun Statistics() {
    Row {
        Row(
            modifier = Modifier.weight(1f)
        ) {
            IconsWithText(count = "206", iconResId = R.drawable.ic_views_count)
        }

        Row(
            modifier = Modifier.weight(1.5f),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconsWithText(count = "206", iconResId = R.drawable.ic_share)

            IconsWithText(count = "11", iconResId = R.drawable.ic_comment)

            IconsWithText(count = "491", iconResId = R.drawable.ic_like)
        }
    }
}

@Composable
private fun IconsWithText(
    count: String,
    iconResId: Int
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(iconResId),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSecondary
        )

        Spacer(modifier = Modifier.width(4.dp))

        Text(
            text = count,
            color = MaterialTheme.colorScheme.onSecondary,
        )
    }
}

@Composable
@Preview
fun PreviewCardLight() {
    VkNewsClientTheme(darkTheme = false) {
//        Box(
//            modifier = Modifier
//                .background(MaterialTheme.colorScheme.background)
//                .fillMaxSize()
//                .padding(8.dp)
//        ) {
        PostCard()
//        }
    }
}

@Composable
@Preview
fun PreviewCardDark() {
    VkNewsClientTheme(darkTheme = true) {
//        Box(
//            modifier = Modifier
//                .background(MaterialTheme.colorScheme.background)
//                .fillMaxSize()
//                .padding(8.dp)
//        ) {
        PostCard()
//        }
    }
}