package com.raindragonn.favoriteimage.domain.usecase

import com.raindragonn.favoriteimage.domain.entity.Image
import javax.inject.Inject

class ChangeImageLikedState @Inject constructor(
    private val _removeLikeImage: RemoveLikedImage,
    private val _saveLikeImage: SaveLikedImage,
) {
    suspend operator fun invoke(image: Image): Result<Image> =
        runCatching {
            if (image.liked) {
                _removeLikeImage(image)
                image.copy(liked = false)
            } else {
                _saveLikeImage(image).getOrThrow()
            }
        }
}