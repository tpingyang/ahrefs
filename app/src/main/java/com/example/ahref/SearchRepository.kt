package com.example.ahref

class SearchRepository {
    private val searchService = RetrofitInstance.searchService

    suspend fun getSearch(searchTerm: String): List<SearchResult> {
        return searchService.getSearch(searchTerm)
    }
}