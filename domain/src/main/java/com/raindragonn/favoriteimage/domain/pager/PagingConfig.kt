package com.raindragonn.favoriteimage.domain.pager

/**
 * [Pager]에서 필요한 설정값을 위한 데이터 클래스
 *
 * @property pagerSize 로드할 페이지 별 크기
 * @property loadDistance 로드할 시점을 의미, pagerSize 와의 차이가 벌어진 경우 데이터를 추가로 불러온다.
 * @property initialLoadSize 초기 데이터 사이즈
 */
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
