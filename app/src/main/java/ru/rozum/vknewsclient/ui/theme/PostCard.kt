package ru.rozum.vknewsclient.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.rozum.vknewsclient.R

@Composable
fun PostCard() {
    Card(
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 1.dp),
        shape = RoundedCornerShape(2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        ) {
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

            Text(
                text = "Test",
                fontSize = 14.sp,
                modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
            )

            Image(
                painter = painterResource(id = R.drawable.post_content_image),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Crop
            )

            Row(
                modifier = Modifier
                    .padding(vertical = 8.dp, horizontal = 4.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
            ) {
                ButtonBottomPost(count = "206", painterResource(id = R.drawable.ic_views_count))

                Spacer(modifier = Modifier.weight(1f))

                ButtonBottomPost(count = "206", painterResource(id = R.drawable.ic_share))

                ButtonBottomPost(count = "11", painterResource(id = R.drawable.ic_comment))

                ButtonBottomPost(count = "491", painterResource(id = R.drawable.ic_like))

            }
        }
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

@Composable
private fun ButtonBottomPost(
    count: String,
    image: Painter
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = count,
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier.padding(end = 4.dp, start = 4.dp)
        )
        Image(painter = image, contentDescription = null, modifier = Modifier.size(18.dp))
    }
}