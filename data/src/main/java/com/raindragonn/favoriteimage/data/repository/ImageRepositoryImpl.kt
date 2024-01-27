package com.raindragonn.favoriteimage.data.repository

import com.raindragonn.favoriteimage.data.database.dao.ImageDao
import com.raindragonn.favoriteimage.data.database.entity.ImageData
import com.raindragonn.favoriteimage.data.remote.api.SearchApi
import com.raindragonn.favoriteimage.domain.di.IoDispatcher
import com.raindragonn.favoriteimage.domain.entity.Image
import com.raindragonn.favoriteimage.domain.repository.ImageRepository
import com.raindragonn.favoriteimage.domain.util.ListEmptyException
import com.raindragonn.favoriteimage.domain.util.OverPageException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    @IoDispatcher
    private val _dispatcher: CoroutineDispatcher,
    private val _api: SearchApi,
    private val _imageDao: ImageDao,
) : ImageRepository {

    override suspend fun searchImage(query: String, page: Int, perPage: Int): List<Image> =
        withContext(_dispatcher) {
            val response = _api.getSongList(query, page, perPage)

            if (response.results.isEmpty()) throw ListEmptyException
            if (response.total > 0 && response.totalPages < page) {
                throw OverPageException(response.totalPages, page)
            }

            response
                .results
                .map { imageResponse ->
                    Image(
                        id = imageResponse.id,
                        thumbnailUrl = imageResponse.urls.thumb,
                        originUrl = imageResponse.urls.full,
                        author = imageResponse.user.username,
                        width = imageResponse.width,
                        height = imageResponse.height,
                        createdAt = imageResponse.createdAt,
                    )
                }
        }

    override suspend fun insertLikedImage(image: Image): Long = withContext(_dispatcher) {
        _imageDao.insert(image.mapToData().copy(liked = true))
    }

    override suspend fun getById(id: Long): Image? = withContext(_dispatcher) {
        _imageDao.getById(id)?.mapToEntity()
    }

    override suspend fun getByImageId(imageId: String): Image? = withContext(_dispatcher) {
        _imageDao.getByImageId(imageId)?.mapToEntity()
    }

    override suspend fun removeByImageId(id: String) = withContext(_dispatcher) {
        _imageDao.deleteByImageId(id)
    }


    override suspend fun getAllLikedImages(): List<Image> = withContext(_dispatcher) {
        _imageDao.getAllLikedImage().map {
            it.mapToEntity()
        }
    }

    override fun getAllLikedImageFlow(): Flow<List<Image>> =
        _imageDao.getAllLikedImageFlow()
            .map { list ->
                list.map {
                    it.mapToEntity()
                }
            }

    private fun Image.mapToData(): ImageData {
        return ImageData(
            imageId = id,
            thumbnailUrl = thumbnailUrl,
            originUrl = originUrl,
            author = author,
            width = width,
            height = height,
            createdAt = createdAt,
            liked = liked,
        )
    }

    private fun ImageData.mapToEntity(): Image {
        return Image(
            id = imageId,
            thumbnailUrl = thumbnailUrl,
            originUrl = originUrl,
            author = author,
            width = width,
            height = height,
            createdAt = createdAt,
            liked = liked,
        )
    }

}