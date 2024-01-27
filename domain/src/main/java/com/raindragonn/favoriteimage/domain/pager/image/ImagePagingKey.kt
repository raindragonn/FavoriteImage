package com.raindragonn.favoriteimage.domain.pager.image

data class ImagePagingKey(
    val query: String,
    val page: Int = 1
)