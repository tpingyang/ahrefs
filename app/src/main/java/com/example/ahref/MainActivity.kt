package com.example.ahref

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.example.ahref.search.ui.SearchFragment
import com.example.ahref.search.ui.SearchViewModel


class MainActivity : AppCompatActivity(R.layout.main_activity), MainFragment.StartSearchListener,
    SearchViewModel.SearchScreenListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add(R.id.fragment_container_view, MainFragment::class.java, null)
            }
        }
    }

    override fun startSearch(queryString: String) {
        val bundle = Bundle()
        bundle.putString(SearchFragment.INITIAL_STRING, queryString)
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add(R.id.fragment_container_view, SearchFragment::class.java, bundle)
            addToBackStack("startSearch")
        }
    }

    override fun closeSearchFragment() {
        supportFragmentManager.popBackStackImmediate()
    }

    override fun onSearch(searchQuery: String) {
        val browserIntent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("https://duckduckgo.com/?va=e&t=ha&q=$searchQuery")
        )
        startActivity(browserIntent)
    }
}