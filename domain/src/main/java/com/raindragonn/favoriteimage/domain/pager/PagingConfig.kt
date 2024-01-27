package com.raindragonn.favoriteimage.domain.pager

data class PagingConfig(
    val pagerSize: Int,
    val loadDistance: Int,
    val initialLoadSize: Int = pagerSize * DEFAULT_INITIAL_PAGE_MULTIPLIER,
) {
    init {
        if (loadDistance !in 0..pagerSize) {
            throw IllegalArgumentException("loadDistance must be a value between 0 and pagerSize. loadDistance: $loadDistance, pagerSize: $pagerSize")
        }
    }

    companion object {
        private const val DEFAULT_INITIAL_PAGE_MULTIPLIER = 3
    }
}
