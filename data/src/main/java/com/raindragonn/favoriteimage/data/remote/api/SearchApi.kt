package com.raindragonn.favoriteimage.data.remote.api

import com.raindragonn.favoriteimage.data.BuildConfig
import com.raindragonn.favoriteimage.data.remote.response.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApi {
    @GET(
        "search/photos" +
                "?client_id=${BuildConfig.API_KEY}"
    )
    suspend fun getSongList(
        @Query("query") query: String,
        @Query("page") page: Int = INITIAL_PAGE,
        @Query("per_page") perPage: Int,
    ): SearchResponse

    companion object {
        private const val INITIAL_PAGE = 1
    }
}