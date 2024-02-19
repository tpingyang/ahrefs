package com.example.ahref.search.ui

import com.example.ahref.RetrofitInstance

class SearchRepository {
    private val searchService = RetrofitInstance.searchService

    suspend fun getSearch(searchTerm: String): List<SearchResult> {
        return searchService.getSearch(searchTerm)
    }
}