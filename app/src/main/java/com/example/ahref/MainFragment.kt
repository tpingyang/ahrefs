package com.example.ahref

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import com.example.ahref.search.ui.SearchScreen
import kotlinx.coroutines.flow.MutableStateFlow


class MainFragment : Fragment() {

    interface StartSearchListener {
        fun startSearch(queryString: String)
    }

    private val searchText = MutableStateFlow("")
    private lateinit var startSearchListener: StartSearchListener

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        startSearchListener = activity as StartSearchListener

        return ComposeView(requireContext()).apply {
            setContent {
                val searchString by searchText.collectAsState()
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    BasicTextField(
                        value = searchString,
                        onValueChange = { searchText.value = it },
                        textStyle = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color.Black
                        ),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                        keyboardActions = KeyboardActions(onSearch = {}),
                        singleLine = true,
                        modifier = Modifier
                            .wrapContentHeight()
                            .padding(horizontal = 16.dp),
                        decorationBox = { innerTextField ->
                            Box(
                                contentAlignment = Alignment.CenterStart,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight()
                                    .border(
                                        width = 1.dp,
                                        color = Color.LightGray,
                                        shape = RoundedCornerShape(size = 15.dp)
                                    )
                                    .padding(horizontal = 16.dp, vertical = 12.dp) // inner padding

                            ) {
                                innerTextField()
                            }
                        }
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { startSearchListener.startSearch(searchString) }) {
                        Text("Start Search")
                    }
                }
            }
        }
    }
}