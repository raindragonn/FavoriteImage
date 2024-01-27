package com.raindragonn.favoriteimage.data.pager

import com.raindragonn.favoriteimage.domain.entity.Image
import com.raindragonn.favoriteimage.domain.pager.PagingSource
import com.raindragonn.favoriteimage.domain.pager.image.ImagePagingKey
import com.raindragonn.favoriteimage.domain.repository.ImageRepository
import javax.inject.Inject

class ImagePagingSource @Inject constructor(
    private val _repository: ImageRepository,
) : PagingSource<ImagePagingKey, Image>() {

    override suspend fun load(loadParam: LoadParam<ImagePagingKey>): LoadResult<ImagePagingKey, Image> {
        val query = loadParam.key?.query ?: return LoadResult.Error(NullPointerException())
        val page = loadParam.key?.page ?: 1
        return runCatching {
            _repository.searchImage(
                query,
                page = page,
                perPage = loadParam.loadSize
            )
        }.fold(
            onSuccess = { images ->
                val nextPage = if (images.isEmpty()) {
                    1
                } else {
                    page + 1
                }
                val nextKey = ImagePagingKey(query, nextPage)

                LoadResult.Success(
                    data = images,
                    nextKey = nextKey
                )
            },
            onFailure = {
                LoadResult.Error(it)
            }
        )
    }
}