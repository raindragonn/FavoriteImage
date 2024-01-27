package com.raindragonn.favoriteimage.domain.pager

import com.raindragonn.favoriteimage.domain.util.ListEmptyException
import com.raindragonn.favoriteimage.domain.util.OverPageException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 *
 * @param Key [PagingSource]에서 추가 데이터를 로드하기 위해 사용하는 페이지 쿼리 유형
 * @param Value 페이저를 통해 가져올 데이터 타입*
 */
class Pager<Key : Any, Value : Any>(
    initialKey: Key? = null,
    private val _pagingConfig: PagingConfig,
    private val _pagingSource: PagingSource<Key, Value>,
    private val _coroutineScope: CoroutineScope
) : PagingBindListener {

    private val _flow: MutableStateFlow<List<Value>> = MutableStateFlow(emptyList())
    val flow: StateFlow<List<Value>>
        get() = _flow.asStateFlow()

    private val _loadStateFlow: MutableStateFlow<LoadState> = MutableStateFlow(LoadState.NotLoading)
    val loadStateFlow: StateFlow<LoadState>
        get() = _loadStateFlow.asStateFlow()

    private var _lastKey: Key? = initialKey

    override fun bindListener(listSize: Int, currentBindPosition: Int) {
        _coroutineScope.launch {

            val loadState: LoadState = _loadStateFlow.value
            if (loadState != LoadState.NotLoading) return@launch

            val checkBindPosition: Boolean =
                currentBindPosition >= listSize - _pagingConfig.loadDistance
            val checkInitLoadSize: Boolean = listSize < _pagingConfig.initialLoadSize
            if (checkInitLoadSize || checkBindPosition) {
                loadPage()
            }
        }
    }

    private suspend fun loadPage() {
        _loadStateFlow.emit(LoadState.Loading)
        val pagerSize: Int = _pagingConfig.pagerSize
        val result: PagingSource.LoadResult<Key, Value> =
            _pagingSource.load(PagingSource.LoadParam(_lastKey, pagerSize))
        when (result) {
            is PagingSource.LoadResult.Success -> {
                val currentList: List<Value> = _flow.value + result.data
                _flow.emit(currentList)
                _lastKey = result.nextKey
                _loadStateFlow.emit(LoadState.NotLoading)
            }

            is PagingSource.LoadResult.Error -> {
                _loadStateFlow.emit(LoadState.Error(result.throwable))

                if (result.throwable !is ListEmptyException && result.throwable !is OverPageException) {
                    delay(3000L)
                    _loadStateFlow.emit(LoadState.NotLoading)
                }
            }
        }
    }
}