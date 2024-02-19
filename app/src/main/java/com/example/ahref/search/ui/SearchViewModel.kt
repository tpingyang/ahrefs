package com.example.ahref.search.ui

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.w3c.dom.Text
import java.lang.Exception

class SearchViewModel(private val searchScreenListener: SearchScreenListener) : ViewModel() {

    interface SearchScreenListener {
        fun closeSearchFragment()
        fun onSearch(searchQuery: String)
    }

    private val repository = SearchRepository()

    private val _searchText = MutableStateFlow(TextFieldValue(""))
    val searchText = _searchText.asStateFlow()

    private val _searchResult = MutableLiveData<List<SearchResult>>(emptyList())
    val searchResult: LiveData<List<SearchResult>> = _searchResult

    private fun fetchSearchResult() {
        viewModelScope.launch {
            try {
                val searchResult = repository.getSearch(searchText.value.text)
                _searchResult.value = searchResult
            } catch (e: Exception) {
                //ignore
            }
        }
    }

    fun onSearchTextChange(text: String) {
        onSearchTextChange(TextFieldValue(text, selection = TextRange(text.length)))
    }

    fun onSearchTextChange(text: TextFieldValue) {
        val toSearch = _searchText.value.text != text.text
        _searchText.value = text
        if (toSearch) fetchSearchResult()
    }

    fun onClear() {
        onSearchTextChange(TextFieldValue(""))
    }

    fun onSearch(queryString: String) {
        searchScreenListener.onSearch(queryString)
    }

    fun onBack() {
        searchScreenListener.closeSearchFragment()
    }
}