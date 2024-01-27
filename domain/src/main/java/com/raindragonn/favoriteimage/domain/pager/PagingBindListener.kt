package com.raindragonn.favoriteimage.domain.pager

interface PagingBindListener {
    fun bindListener(listSize: Int, currentBindPosition: Int)
}