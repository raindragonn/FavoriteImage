package com.raindragonn.favoriteimage.domain.pager

sealed interface LoadState {

    data object Loading : LoadState

    data object NotLoading : LoadState

    data class Error(
        val throwable: Throwable
    ) : LoadState
}