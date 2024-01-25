package com.raindragonn.favoriteimage.domain.usecase

import com.raindragonn.favoriteimage.domain.di.IoDispatcher
import com.raindragonn.favoriteimage.domain.repository.ImageRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SearchImage @Inject constructor(
    @IoDispatcher
    private val _dispatcher: CoroutineDispatcher,
    private val _imageRepository: ImageRepository
) {
    suspend operator fun invoke(query: String, page: Int, perPage: Int) =
        withContext(_dispatcher) {
            runCatching {
                _imageRepository.searchImage(query, page, perPage)
            }
        }
}