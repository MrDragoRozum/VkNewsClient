package ru.rozum.vknewsclient.data.model

import com.google.gson.annotations.SerializedName

data class PhotoDto(
    @SerializedName("sizes") val photoUrlDto: List<PhotoUrlDto>
)
