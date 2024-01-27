package com.raindragonn.favoriteimage.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raindragonn.favoriteimage.domain.entity.Image
import com.raindragonn.favoriteimage.domain.pager.LoadState
import com.raindragonn.favoriteimage.domain.pager.Pager
import com.raindragonn.favoriteimage.domain.pager.image.ImagePagingKey
import com.raindragonn.favoriteimage.domain.usecase.GetAllLikedImage
import com.raindragonn.favoriteimage.domain.usecase.GetImagePager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    getAllLikedImage: GetAllLikedImage,
    private val _getImagePager: GetImagePager,
) : ViewModel() {

    private val likeImageState: Flow<List<Image>> = getAllLikedImage()

    private val _imageListStateFlow: MutableStateFlow<List<Image>> = MutableStateFlow(emptyList())
    val imageListStateFlow: StateFlow<List<Image>>
        get() = _imageListStateFlow.asStateFlow()

    private val _loadStateFlow: MutableStateFlow<LoadState> = MutableStateFlow(LoadState.NotLoading)
    val loadStateFlow: StateFlow<LoadState>
        get() = _loadStateFlow.asStateFlow()

    fun getImagePager(query: String): Pager<ImagePagingKey, Image> =
        _getImagePager(query, viewModelScope)

    fun setSearchImageFlow(list: Flow<List<Image>>) = viewModelScope.launch {
        list
            .combine(likeImageState) { searchImageList: List<Image>, likedImageList: List<Image> ->
                searchImageList.map { searchImage ->
                    val isLiked = likedImageList.any { likedImage ->
                        searchImage.id == likedImage.id
                    }
                    searchImage.copy(liked = isLiked)
                }
            }
            .stateIn(this)
            .collect(_imageListStateFlow::emit)
    }

    fun setLoadStateFlow(loadState: Flow<LoadState>) = viewModelScope.launch {
        loadState
            .stateIn(this)
            .collect(_loadStateFlow::emit)
    }

}