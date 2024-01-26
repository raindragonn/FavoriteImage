package com.raindragonn.favoriteimage.domain.usecase

import com.raindragonn.favoriteimage.domain.di.IoDispatcher
import com.raindragonn.favoriteimage.domain.entity.Image
import com.raindragonn.favoriteimage.domain.repository.ImageRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SaveLikedImage @Inject constructor(
    @IoDispatcher
    private val _dispatcher: CoroutineDispatcher,
    private val _repository: ImageRepository,
) {
    suspend operator fun invoke(image: Image) = withContext(_dispatcher) {
        runCatching {
            val id = _repository.insertLikedImage(image)
            _repository.getById(id) ?: throw NullPointerException("Not Found Image for Database")
        }
    }
}