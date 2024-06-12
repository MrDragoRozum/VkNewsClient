package ru.rozum.vknewsclient.data.mapper

import ru.rozum.vknewsclient.data.model.NewsFeedResponseDto
import ru.rozum.vknewsclient.domain.FeedPost
import ru.rozum.vknewsclient.domain.StatisticItem
import ru.rozum.vknewsclient.domain.StatisticType
import kotlin.math.absoluteValue

class NewsFeedMapper {

    fun NewsFeedResponseDto.toPosts(): List<FeedPost> {
        val result = mutableListOf<FeedPost>()

        val posts = newsFeedContent.posts
        val groups = newsFeedContent.groups

        for (post in posts) {
            val group = groups.find { it.id == post.communityId.absoluteValue } ?: continue
            val feedPost = FeedPost(
                id = post.id,
                communityName = group.name,
                publicationDate = post.date.toString(),
                communityImageUrl = group.imageUri,
                contentText = post.text,
                contentImageUrl = post.attachments?.firstOrNull()?.photo?.photoUrlDto?.lastOrNull()?.url,
                statistics = listOf(
                    StatisticItem(type = StatisticType.LIKES, post.likes.count),
                    StatisticItem(type = StatisticType.VIEWS, post.views.count),
                    StatisticItem(type = StatisticType.SHARES, post.reposts.count),
                    StatisticItem(type = StatisticType.COMMENTS, post.comments.count)
                )
            )
            result.add(feedPost)
        }
        return result
    }
}