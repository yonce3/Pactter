package com.yonce3.pactter.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.yonce3.pactter.PacListViewAdapter
import com.yonce3.pactter.R
import com.yonce3.pactter.data.AppDatabase
import com.yonce3.pactter.viewModel.HomeViewModel

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var toolbar: Toolbar
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var layoutManager: RecyclerView.LayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.home_fragment, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider.NewInstanceFactory().create(HomeViewModel::class.java)
        // TODO: Use the ViewModel
        setHasOptionsMenu(true)

        // TODO: あとで削除
        var db = Room.databaseBuilder(
            activity!!.applicationContext, AppDatabase::class.java, "database-name").build()

        // リストビューの作成
        layoutManager = LinearLayoutManager(activity)

        viewAdapter = PacListViewAdapter(db.pacDao().getAll())
        recyclerView = view!!.findViewById<RecyclerView>(R.id.recyclerView).also {
            it.layoutManager = layoutManager
            it.adapter = viewAdapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.search -> true
        }
        return super.onOptionsItemSelected(item)
    }
}