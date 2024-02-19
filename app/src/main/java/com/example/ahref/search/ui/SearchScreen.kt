package com.example.ahref.search.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.StateFlow

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SearchScreen(
    searchText: StateFlow<TextFieldValue>,
    searchResult: LiveData<List<SearchResult>>,
    onQueryChange: (TextFieldValue) -> Unit,
    onSearch: (String) -> Unit,
    onBack: () -> Unit,
    onClear: () -> Unit,
) {


    val searchText by searchText.collectAsState()
    val searchResults by searchResult.observeAsState()

    Scaffold(
        topBar = {
            MySearchBar(
                query = searchText,
                onQueryChange = { onQueryChange(it) },
                onSearch = onSearch,
                active = true,
                onActiveChange = {},
                leadingIcon = {
                    IconButton(
                        onClick = { onBack() },
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                        )

                    }
                },
                trailingIcon = {
                    IconButton(onClick = { onClear() }) {
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
                    modifier = Modifier.padding(horizontal = 24.dp)
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
                                    Modifier
                                        .padding(4.dp)
                                        .size(30.dp)
                                )
                                Text(
                                    text = AnnotatedString(
                                        text = searchResult.phrase,
                                        spanStyles = listOf(
                                            AnnotatedString.Range(
                                                SpanStyle(fontWeight = FontWeight.Bold),
                                                start = if (searchResult.phrase.length > searchText.text.length) {
                                                    searchText.text.length
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
                                IconButton(onClick = {
                                    onQueryChange(
                                        TextFieldValue(
                                            searchResult.phrase,
                                            selection = TextRange(searchResult.phrase.length)
                                        )
                                    )
                                }) {
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
    ) {}
}

@Composable
fun MySearchBar(
    query: TextFieldValue,
    onQueryChange: (TextFieldValue) -> Unit,
    onSearch: (String) -> Unit,
    active: Boolean,
    onActiveChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    placeHolder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    Box(
        modifier = modifier
    ) {
        Column {
            BasicTextField(
                value = query,
                onValueChange = onQueryChange,
                textStyle = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black
                ),
                enabled = enabled,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = { onSearch(query.text) }),
                singleLine = true,
                modifier = Modifier
                    .height(56.dp)
                    .padding(horizontal = 8.dp)
                    .focusRequester(focusRequester)
                    .onFocusChanged { onActiveChange(it.isFocused) }
                    .semantics {
                        onClick {
                            focusRequester.requestFocus()
                            true
                        }
                    },
                decorationBox = { innerTextField ->
                    Box(
                        contentAlignment = Alignment.CenterStart,
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                width = 1.dp,
                                color = Color.LightGray,
                                shape = RoundedCornerShape(size = 15.dp)
                            )
                            .padding(horizontal = 4.dp, vertical = 12.dp) // inner padding

                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .align(Alignment.CenterStart)
                            ) {
                                leadingIcon?.let { it() }
                                Spacer(modifier = Modifier.width(8.dp))
                                Box {
                                    if (query.text.isEmpty()) {
                                        placeHolder?.let { it() }
                                    }
                                    innerTextField()
                                }
                            }
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .align(Alignment.CenterEnd)
                            ) {
                                trailingIcon?.let { it() }
                            }
                        }
                    }
                }
            )
            Divider(
                color = Color.LightGray,
                thickness = 1.5.dp,
                modifier = Modifier.padding(top = 16.dp, bottom = 4.dp)
            )
            content()
        }
        LaunchedEffect(active) {
            if (!active) {
                focusManager.clearFocus()
            } else {
                focusRequester.requestFocus()
            }
        }
    }
}
