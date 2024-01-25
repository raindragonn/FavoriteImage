package com.raindragonn.favoriteimage.domain.repository

import com.raindragonn.favoriteimage.domain.entity.Image

interface ImageRepository {

    suspend fun searchImage(query: String, page: Int, perPage: Int): List<Image>
}