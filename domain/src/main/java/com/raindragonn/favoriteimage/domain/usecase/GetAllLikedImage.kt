package com.raindragonn.favoriteimage.domain.usecase

import com.raindragonn.favoriteimage.domain.repository.ImageRepository
import javax.inject.Inject

class GetAllLikedImage @Inject constructor(
    private val _repository: ImageRepository,
) {
    operator fun invoke() = _repository.getAllLikedImageFlow()
}