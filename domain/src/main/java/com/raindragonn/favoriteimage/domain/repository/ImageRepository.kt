package com.raindragonn.favoriteimage.domain.repository

import com.raindragonn.favoriteimage.domain.entity.Image
import kotlinx.coroutines.flow.Flow

interface ImageRepository {

    suspend fun searchImage(query: String, page: Int, perPage: Int): List<Image>

    suspend fun insertLikedImage(image: Image): Long

    suspend fun getByImageId(imageId: String): Image?

    suspend fun getById(id: Long): Image?

    suspend fun removeByImageId(id: String)

    suspend fun getAllLikedImages(): List<Image>

    fun getAllLikedImageFlow(): Flow<List<Image>>
}