package com.raindragonn.favoriteimage.domain.usecase

import com.raindragonn.favoriteimage.domain.entity.Image
import com.raindragonn.favoriteimage.domain.pager.Pager
import com.raindragonn.favoriteimage.domain.pager.PagingConfig
import com.raindragonn.favoriteimage.domain.pager.PagingSource
import com.raindragonn.favoriteimage.domain.pager.image.ImagePagingKey
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class GetImagePager @Inject constructor(
    private val _imagePagingSource: PagingSource<ImagePagingKey, Image>
) {
    operator fun invoke(
        query: String,
        coroutineScope: CoroutineScope,
    ): Pager<ImagePagingKey, Image> = Pager(
        initialKey = ImagePagingKey(
            query = query
        ),
        _pagingConfig = PagingConfig(
            pagerSize = LOAD_PAGE_SIZE,
            loadDistance = LOAD_DISTANCE,
        ),
        _pagingSource = _imagePagingSource,
        _coroutineScope = coroutineScope,
    )

    companion object {
        private const val LOAD_PAGE_SIZE = 50
        private const val LOAD_DISTANCE = 12
    }
}