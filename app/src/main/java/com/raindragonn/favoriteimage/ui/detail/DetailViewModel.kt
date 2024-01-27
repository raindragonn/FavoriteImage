package com.raindragonn.favoriteimage.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raindragonn.favoriteimage.domain.entity.Image
import com.raindragonn.favoriteimage.domain.usecase.ChangeImageLikedState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val _savedStateHandle: SavedStateHandle,
    private val _changeImageLikedState: ChangeImageLikedState,
) : ViewModel() {

    private val imageFromSavedState: Image
        get() = DetailFragmentArgs.fromSavedStateHandle(_savedStateHandle).image

    private val _imageState: MutableStateFlow<Image> = MutableStateFlow(imageFromSavedState)
    val imageState: StateFlow<Image>
        get() = _imageState.asStateFlow()

    fun onClick() = viewModelScope.launch {
        _changeImageLikedState(_imageState.value)
            .onSuccess { _imageState.emit(it) }
    }
}