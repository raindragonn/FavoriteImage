package com.raindragonn.favoriteimage.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raindragonn.favoriteimage.domain.entity.Image
import com.raindragonn.favoriteimage.domain.usecase.SearchImage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val _searchImage: SearchImage
) : ViewModel() {

    private val _imageListState: MutableStateFlow<Result<List<Image>>> =
        MutableStateFlow(Result.success(emptyList()))
    val imageListState: StateFlow<Result<List<Image>>>
        get() = _imageListState.asStateFlow()

    fun search(query: String) {
        viewModelScope.launch {
            _imageListState.emit(_searchImage(query, 1, 10))
        }
    }

}