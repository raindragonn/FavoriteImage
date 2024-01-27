package com.raindragonn.favoriteimage.domain.pager

/**
 * 페이지 네이션의 상태를 나타낸다.
 *
 * [Loading] 현재 항목을 로드 중이다.
 * [NotLoading] 현재 항목을 로드하고 있지 않다.
 * [Error] 로드 중 오류가 발생했다.
 */
sealed interface LoadState {

    data object Loading : LoadState

    data object NotLoading : LoadState

    data class Error(
        val throwable: Throwable
    ) : LoadState
}