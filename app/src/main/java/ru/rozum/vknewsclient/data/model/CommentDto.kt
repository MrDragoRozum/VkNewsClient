package ru.rozum.vknewsclient.data.model

import com.google.gson.annotations.SerializedName

data class CommentDto(
    @SerializedName("id") val id: Long,
    @SerializedName("from_id") val userId: Long,
    @SerializedName("date") val date: Long,
    @SerializedName("text") val text: String,

)
