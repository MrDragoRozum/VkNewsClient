package ru.rozum.vknewsclient.data.model

import com.google.gson.annotations.SerializedName

data class ProfileDto(
    @SerializedName("id") val userId: Long,
    @SerializedName("photo_100") val authorAvatarUri: String,
    @SerializedName("first_name") val name: String,
    @SerializedName("last_name") val lastName: String
)