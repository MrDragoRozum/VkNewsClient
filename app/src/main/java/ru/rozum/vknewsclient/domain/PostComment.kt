package ru.rozum.vknewsclient.domain

import ru.rozum.vknewsclient.R

data class PostComment(
    val id: Long,
    val authorName: String,
    val authorAvatarUrl: String,
    val commentText: String,
    val publicationDate: String,
)