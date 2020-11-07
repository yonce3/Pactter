package com.yonce3.pactter.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yonce3.pactter.R
import com.yonce3.pactter.viewModel.SearchViewModel

class SearchFragment : Fragment() {

    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var viewAdapter: ArticleListViewAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView

    private val viewModel: SearchViewModel by lazy {
        activity?.run {
            ViewModelProvider.AndroidViewModelFactory(application).create(SearchViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        searchView = view.findViewById(R.id.search_view)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        searchView.setOnQueryTextListener(SearchViewListener(viewModel))
        searchView.setIconifiedByDefault(false)

        // リストビューの設定
        layoutManager = LinearLayoutManager(activity)
        viewAdapter = ArticleListViewAdapter()

        viewModel.articles.observe(viewLifecycleOwner, Observer { it ->
            it?.let { viewAdapter.setArticles(it) }
        })

        recyclerView = view!!.findViewById<RecyclerView>(R.id.recyclerView).also {
            it.layoutManager = layoutManager
            it.adapter = viewAdapter
        }
    }

    class SearchViewListener(val viewModel: SearchViewModel): OnQueryTextListener {
        override fun onQueryTextChange(newText: String?): Boolean {
            println("changed")
            viewModel.searchArticles()
            return true
        }

        override fun onQueryTextSubmit(query: String?): Boolean {
            return true
        }
    }
}