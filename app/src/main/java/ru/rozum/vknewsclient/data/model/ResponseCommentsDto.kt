package ru.rozum.vknewsclient.data.model

import com.google.gson.annotations.SerializedName
import ru.rozum.vknewsclient.domain.PostComment
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class ResponseCommentsDto(
    @SerializedName("response") val commentsContent: CommentsContentDto
)

fun ResponseCommentsDto.toPostComments(): List<PostComment> {
    val result = mutableListOf<PostComment>()

    val comments = commentsContent.comments
    val profiles = commentsContent.profiles

    comments.forEach { comment ->
        if(comment.text.isBlank()) return@forEach

        val currentProfile = profiles.find {
            it.userId == comment.userId
        } ?: return@forEach

        val authorName = "${currentProfile.name} ${currentProfile.lastName}"

        val postComment = PostComment(
            id = comment.id,
            authorName = authorName,
            authorAvatarUrl = currentProfile.authorAvatarUri,
            commentText = comment.text,
            publicationDate = comment.date.toDate()
        )

        result.add(postComment)
    }

    return result
}

private fun Long.toDate(): String =
    SimpleDateFormat("d MMMM yyyy, hh:mm", Locale.getDefault()).format(Date(this * 1000))