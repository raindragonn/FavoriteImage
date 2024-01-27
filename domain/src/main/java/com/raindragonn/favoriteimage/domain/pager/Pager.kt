package com.raindragonn.favoriteimage.domain.pager

import com.raindragonn.favoriteimage.domain.util.ListEmptyException
import com.raindragonn.favoriteimage.domain.util.OverPageException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class Pager<Key : Any, Value : Any>(
    initialKey: Key? = null,
    private val _pagerConfig: PagingConfig,
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

            val loadState = _loadStateFlow.value
            if (loadState != LoadState.NotLoading) return@launch

            val checkBindPosition = currentBindPosition >= listSize - _pagerConfig.loadDistance
            val checkInitLoadSize = listSize < _pagerConfig.initialLoadSize
            if (checkInitLoadSize || checkBindPosition) {
                loadPage()
            }
        }
    }

    private suspend fun loadPage() {
        _loadStateFlow.emit(LoadState.Loading)
        val pagerSize = _pagerConfig.pagerSize
        val result =
            _pagingSource.load(PagingSource.LoadParam(_lastKey, pagerSize))
        when (result) {
            is PagingSource.LoadResult.Success -> {
                val currentList = _flow.value + result.data
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