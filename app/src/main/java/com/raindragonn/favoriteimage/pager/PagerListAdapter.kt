package com.raindragonn.favoriteimage.pager

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.raindragonn.favoriteimage.domain.pager.PagingBindListener

abstract class PagerListAdapter<T, VH : RecyclerView.ViewHolder>(
    private val _pagingBindListener: PagingBindListener,
    diffCallBack: DiffUtil.ItemCallback<T>
) : ListAdapter<T, VH>(diffCallBack) {
    init {
        _pagingBindListener.bindListener(0, 0)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        onBindViewHolder(holder, getItem(position))
        _pagingBindListener.bindListener(currentList.size, position)
    }

    abstract fun onBindViewHolder(holder: VH, item: T)
}