package com.raindragonn.favoriteimage.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class Image(
    val id: String,
    val thumbnailUrl: String,
    val originUrl: String,
    val author: String,
    val width: Int,
    val height: Int,
    val isLike: Boolean = false,
) : java.io.Serializable
