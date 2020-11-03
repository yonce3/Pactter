package com.yonce3.pactter.ui

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yonce3.pactter.R
import com.yonce3.pactter.util.REQUEST_PAC_DETAIL
import com.yonce3.pactter.viewModel.HomeViewModel

class HomeFragment : Fragment() {

    private lateinit var toolbar: Toolbar
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: PacListViewAdapter
    private lateinit var layoutManager: RecyclerView.LayoutManager

    private val viewModel: HomeViewModel by lazy {
        activity?.run {
            ViewModelProvider.AndroidViewModelFactory(application).create(HomeViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.home_fragment, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)

        // リストビューの作成
        layoutManager = LinearLayoutManager(activity)
        viewAdapter = PacListViewAdapter()
        viewAdapter.setOnItemClickListener(object: PacListViewAdapter.OnItemClickListener{
            override fun onItemClick(view: View, position: Int, pacId: Int) {
                Toast.makeText(activity!!.applicationContext, "${pacId}がタップされました", Toast.LENGTH_SHORT).show()

                val intent = Intent(activity, PacDetailActivity::class.java)
                intent.putExtra("pacId", pacId)
                startActivityForResult(intent, REQUEST_PAC_DETAIL)
            }
        })

        viewModel.allPacs.observe(viewLifecycleOwner, Observer { it ->
            it?.let { viewAdapter.setPacs(it) }
        })

        val divider = androidx.recyclerview.widget.DividerItemDecoration(context, LinearLayoutManager(activity).orientation)
        recyclerView = view!!.findViewById<RecyclerView>(R.id.recyclerView).also {
            it.layoutManager = layoutManager
            it.adapter = viewAdapter
            it.addItemDecoration(divider)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.home_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.search -> true
        }
        return super.onOptionsItemSelected(item)
    }
}