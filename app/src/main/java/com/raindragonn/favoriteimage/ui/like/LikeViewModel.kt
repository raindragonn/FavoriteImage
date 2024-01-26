package com.raindragonn.favoriteimage.ui.like

import androidx.lifecycle.ViewModel
import com.raindragonn.favoriteimage.domain.entity.Image
import com.raindragonn.favoriteimage.domain.usecase.GetAllLikedImage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class LikeViewModel @Inject constructor(
    private val _getAllLikeImage: GetAllLikedImage
) : ViewModel() {

    fun getLikedImageState(): Flow<List<Image>> = _getAllLikeImage()
}