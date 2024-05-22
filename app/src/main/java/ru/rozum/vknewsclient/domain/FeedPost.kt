package ru.rozum.vknewsclient.domain

import ru.rozum.vknewsclient.R

data class FeedPost(
    val communityName: String = "Типичный программист",
    val publicationDate: String = "14:00",
    val avatarResId: Int = R.drawable.post_comunity_thumbnail,
    val contentText: String = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer commodo massa non mi feugiat laoreet. Vivamus et felis volutpat, lacinia erat nec, consectetur lectus.",
    val contentResId: Int = R.drawable.post_content_image,
    val statistics: List<StatisticItem> = listOf(
        StatisticItem(type = StatisticType.VIEWS, count = 206),
        StatisticItem(type = StatisticType.SHARES, count = 206),
        StatisticItem(type = StatisticType.COMMENTS, count = 11),
        StatisticItem(type = StatisticType.LIKES, count = 491)
    )
)
