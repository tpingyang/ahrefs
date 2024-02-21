package com.example.ahref.search.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment

class SearchFragment : Fragment() {
    companion object {
        const val INITIAL_STRING = "initialString"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val initialString = arguments?.getString(INITIAL_STRING) ?: ""
        val viewModel = SearchViewModel(activity as SearchViewModel.SearchScreenListener)
        viewModel.onSearchTextChange(initialString)
        return ComposeView(requireContext()).apply {
            setContent {
                SearchScreen(
                    searchText = viewModel.searchText,
                    searchResult = viewModel.searchResult,
                    onQueryChange = viewModel::onSearchTextChange,
                    onSearch = viewModel::onSearch,
                    onBack = viewModel::onBack,
                    onClear = viewModel::onClear,
                )
            }
        }
    }
}