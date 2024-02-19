package com.example.ahref

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SearchScreen() {

    val viewModel = SearchViewModel()

    val searchText by viewModel.searchText.collectAsState()
    val searchResults by viewModel.searchResult.observeAsState()

    Scaffold(
        topBar = {
            SearchBar(
                query = searchText,
                onQueryChange = viewModel::onSearchTextChange,
                onSearch = viewModel::onSearchTextChange,
                active = true,
                onActiveChange = {},
                colors = SearchBarDefaults.colors(containerColor = Color.Transparent),
                leadingIcon = {
                    IconButton(onClick = { viewModel.onClear() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                trailingIcon = {
                    IconButton(onClick = { viewModel.onClear() }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Cancel"
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                if (searchResults?.isEmpty() == false) LazyColumn(
                    modifier = Modifier.padding(horizontal = 8.dp)
                ) {
                    searchResults?.let {
                        items(it) { searchResult ->
                            Row(
                                Modifier.height(60.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = "Search",
                                    Modifier.padding(4.dp)
                                )
                                Text(
                                    text = AnnotatedString(
                                        text = searchResult.phrase,
                                        spanStyles = listOf(
                                            AnnotatedString.Range(
                                                SpanStyle(fontWeight = FontWeight.Bold),
                                                start = if (searchResult.phrase.length > searchText.length) {
                                                    searchText.length
                                                } else {
                                                    searchResult.phrase.length
                                                },
                                                end = searchResult.phrase.length
                                            )
                                        )
                                    ),
                                    fontSize = 18.sp,
                                    modifier = Modifier
                                        .padding(
                                            horizontal = 16.dp,
                                        )
                                        .weight(1f)
                                )
                                IconButton(onClick = { viewModel.onSearchTextChange(searchResult.phrase) }) {
                                    Icon(
                                        modifier = Modifier.rotate(45f),
                                        imageVector = Icons.Default.ArrowBack,
                                        contentDescription = "Search",
                                    )
                                }
                            }
                            Divider(color = Color.LightGray)
                        }
                    }
                }
            }
        }
    ) {

    }
}

@Preview
@Composable
fun previewSearchScreen() {
    SearchScreen()
}