package com.raindragonn.favoriteimage.domain.pager

import com.sun.jdi.Value

abstract class PagingSource<Key : Any, Value : Any> {

    abstract suspend fun load(loadParam: LoadParam<Key>): LoadResult<Key, Value>

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