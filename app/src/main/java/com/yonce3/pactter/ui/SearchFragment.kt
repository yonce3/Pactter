package com.yonce3.pactter.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yonce3.pactter.databinding.FragmentSearchBinding
import com.yonce3.pactter.viewModel.SearchViewModel

class SearchFragment : Fragment() {

    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var viewAdapter: ArticleListViewAdapter
    private lateinit var binding: FragmentSearchBinding

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

        // data binding
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        // searchViewの設定
        binding.searchView.also {
            it.setOnQueryTextListener(SearchViewListener(viewModel, binding.root))
            it.setIconifiedByDefault(false)
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // data bindingの追加
        binding.also {
            it.lifecycleOwner = this
            it.viewModel = viewModel
        }

        // リストビューの設定
        layoutManager = LinearLayoutManager(activity)
        viewAdapter = ArticleListViewAdapter()

        viewModel.articles.observe(viewLifecycleOwner, Observer { it ->
            it?.let {
                viewAdapter.setArticles(it)
                binding.progressBar.visibility = View.GONE
            }
        })

        binding.recyclerView.also {
            it.layoutManager = layoutManager
            it.adapter = viewAdapter
        }
    }

    class SearchViewListener(val viewModel: SearchViewModel, val view: View): OnQueryTextListener {
        override fun onQueryTextChange(newText: String): Boolean {
            return false
        }

        override fun onQueryTextSubmit(query: String): Boolean {
            if (query.isNotBlank()) {
                viewModel.searchArticles(query)
            } else {
                viewModel.clearArticles()
            }
            return false
        }
    }
}