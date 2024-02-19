package com.example.ahref

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.lang.Exception

class SearchViewModel : ViewModel() {
    private val repository = SearchRepository()

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _searchResult = MutableLiveData<List<SearchResult>>(emptyList())
    val searchResult: LiveData<List<SearchResult>> = _searchResult

    private fun fetchSearchResult() {
        viewModelScope.launch {
            try {
                val searchResult = repository.getSearch(searchText.value)
                _searchResult.value = searchResult
            } catch (e: Exception) {
                //ignore
            }
        }
    }

    fun onSearchTextChange(text: String) {
        _searchText.value = text
        fetchSearchResult()
    }

    fun onClear() {
        onSearchTextChange("")
    }
}