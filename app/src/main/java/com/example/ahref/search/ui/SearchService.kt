package com.example.ahref.search.ui

import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {
    @GET("ac/?")
    suspend fun getSearch(
        @Query("q") searchTerm: String,
    ): List<SearchResult>
}