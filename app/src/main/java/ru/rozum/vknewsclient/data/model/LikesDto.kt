package ru.rozum.vknewsclient.data.model

import com.google.gson.annotations.SerializedName

data class LikesDto(
    @SerializedName("count") val count: Int
)
