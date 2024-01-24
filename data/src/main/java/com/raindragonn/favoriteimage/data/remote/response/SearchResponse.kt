package com.raindragonn.favoriteimage.data.remote.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchResponse(
    @SerialName("results")
    val results: List<ResultResponse>,
    @SerialName("total")
    val total: Int,
    @SerialName("total_pages")
    val totalPages: Int
)