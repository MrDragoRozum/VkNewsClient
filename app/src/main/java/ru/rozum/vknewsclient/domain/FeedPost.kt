package ru.rozum.vknewsclient.domain

import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize
import ru.rozum.vknewsclient.R

@Parcelize
data class FeedPost(
    val id: Int = 0,
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
) : Parcelable {

    companion object {
        val NavigationType: NavType<FeedPost> = object : NavType<FeedPost>(false) {
            override fun get(bundle: Bundle, key: String): FeedPost? = bundle.getParcelable(key)

            override fun parseValue(value: String): FeedPost =
                Gson().fromJson(value, FeedPost::class.java)

            override fun put(bundle: Bundle, key: String, value: FeedPost) {
                bundle.putParcelable(key, value)
            }
        }
    }
}


