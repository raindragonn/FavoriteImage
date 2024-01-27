package com.raindragonn.favoriteimage.data.remote.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ImageResponse(
    @SerialName("id")
    val id: String,
    @SerialName("height")
    val height: Int,
    @SerialName("width")
    val width: Int,
    @SerialName("urls")
    val urls: UrlsResponse,
    @SerialName("user")
    val user: UserResponse,
    @SerialName("created_at")
    val createdAt: String
)