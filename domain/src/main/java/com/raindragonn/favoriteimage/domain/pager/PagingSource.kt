package com.raindragonn.favoriteimage.domain.pager


/**
 * 실제 데이터를 불러오는 방식을 나타낸다.
 *
 * @param Key
 * @param Value
 */
abstract class PagingSource<Key : Any, Value : Any> {

    abstract suspend fun load(loadParam: LoadParam<Key>): LoadResult<Key, Value>

    /**
     * 데이터 요청에 대한 정보
     *
     * @param Key
     * @property key
     * @property loadSize
     */
    data class LoadParam<Key : Any>(
        val key: Key?,
        val loadSize: Int,
    )

    sealed interface LoadResult<Key : Any, Value : Any> {
        data class Error<Key : Any, Value : Any>(
            val throwable: Throwable,
        ) : LoadResult<Key, Value>

        data class Success<Key : Any, Value : Any>(
            val data: List<Value>,
            val nextKey: Key?
        ) : LoadResult<Key, Value>
    }
}