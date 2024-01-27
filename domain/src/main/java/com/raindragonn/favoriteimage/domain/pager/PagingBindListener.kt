package com.raindragonn.favoriteimage.domain.pager

/**
 *
 * 페이지네이션의 트리거 역할을 하는 리스너
 *
 */
interface PagingBindListener {
    fun bindListener(listSize: Int = 0, currentBindPosition: Int = 0)
}