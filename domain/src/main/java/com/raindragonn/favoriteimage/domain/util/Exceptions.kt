package com.raindragonn.favoriteimage.domain.util

object ListEmptyException : Exception("List is Empty")

class OverPageException(
    maxPage: Int, requestPage: Int
) : Exception("Not Found Next page, max page: $maxPage, requestPage: $requestPage")