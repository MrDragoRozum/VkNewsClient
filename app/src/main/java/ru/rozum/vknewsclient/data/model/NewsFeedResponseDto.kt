package ru.rozum.vknewsclient.data.model

import com.google.gson.annotations.SerializedName
import ru.rozum.vknewsclient.domain.entity.FeedPost
import ru.rozum.vknewsclient.domain.entity.StatisticItem
import ru.rozum.vknewsclient.domain.entity.StatisticType
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.absoluteValue

data class NewsFeedResponseDto(
    @SerializedName("response") val newsFeedContent: NewsFeedContentDto
)

fun NewsFeedResponseDto.toPosts(): List<FeedPost> {
    val result = mutableListOf<FeedPost>()

    val posts = newsFeedContent.posts
    val groups = newsFeedContent.groups

    for (post in posts) {
        val group = groups.find { it.id == post.communityId.absoluteValue } ?: continue
        val feedPost = FeedPost(
            id = post.id,
            communityId = post.communityId,
            communityName = group.name,
            publicationDate = post.date.toDate(),
            communityImageUrl = group.imageUri,
            contentText = post.text,
            contentImageUrl = post.attachments?.firstOrNull()?.photo?.photoUrlDto?.lastOrNull()?.url,
            statistics = listOf(
                StatisticItem(type = StatisticType.LIKES, post.likes.count),
                StatisticItem(type = StatisticType.VIEWS, post.views.count),
                StatisticItem(type = StatisticType.SHARES, post.reposts.count),
                StatisticItem(type = StatisticType.COMMENTS, post.comments.count)
            ),
            isLiked = post.likes.userLikes > 0
        )
        result.add(feedPost)
    }
    return result
}

private fun Long.toDate(): String =
    SimpleDateFormat("d MMMM yyyy, hh:mm", Locale.getDefault()).format(Date(this * 1000))
