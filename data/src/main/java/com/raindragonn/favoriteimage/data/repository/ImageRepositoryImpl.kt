package com.raindragonn.favoriteimage.data.repository

import com.raindragonn.favoriteimage.data.remote.api.SearchApi
import com.raindragonn.favoriteimage.domain.di.IoDispatcher
import com.raindragonn.favoriteimage.domain.entity.Image
import com.raindragonn.favoriteimage.domain.repository.ImageRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    @IoDispatcher
    private val _dispatcher: CoroutineDispatcher,
    private val _api: SearchApi
) : ImageRepository {
    override suspend fun searchImage(query: String, page: Int, perPage: Int): List<Image> =
        withContext(_dispatcher) {
            _api.getSongList(query, page, perPage)
                .results
                .map { response ->
                    Image(
                        id = response.id,
                        thumbnailUrl = response.urls.thumb,
                        originUrl = response.urls.full,
                        author = response.user.username,
                        width = response.width,
                        height = response.height,
                        createdAt = response.createdAt
                    )
                }
        }
}