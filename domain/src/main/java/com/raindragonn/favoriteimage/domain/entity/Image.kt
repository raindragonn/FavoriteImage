package com.raindragonn.favoriteimage.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class Image(
    val id: String,
    val url: String,
    val author: String,
    val width: Int,
    val height: Int,
    val isLike: Boolean = false,
)
