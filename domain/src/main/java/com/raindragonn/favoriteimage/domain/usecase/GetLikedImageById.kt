package com.raindragonn.favoriteimage.domain.usecase

import com.raindragonn.favoriteimage.domain.di.IoDispatcher
import com.raindragonn.favoriteimage.domain.repository.ImageRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetLikedImageById @Inject constructor(
    @IoDispatcher
    private val _dispatcher: CoroutineDispatcher,
    private val _repository: ImageRepository,
) {
    suspend operator fun invoke(id: String) =
        withContext(_dispatcher) {
            runCatching { _repository.getByImageId(id) }
        }
}