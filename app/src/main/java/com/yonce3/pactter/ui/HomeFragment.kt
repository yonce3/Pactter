package com.yonce3.pactter.ui

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.yonce3.pactter.PacListViewAdapter
import com.yonce3.pactter.R
import com.yonce3.pactter.data.AppDatabase
import com.yonce3.pactter.data.entity.Pac
import com.yonce3.pactter.viewModel.HomeViewModel

class HomeFragment : Fragment(), PacListViewAdapter.OnItemClickListener {

    private lateinit var viewModel: HomeViewModel
    private lateinit var toolbar: Toolbar
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: PacListViewAdapter
    private lateinit var layoutManager: RecyclerView.LayoutManager

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
        viewModel = ViewModelProvider.NewInstanceFactory().create(HomeViewModel::class.java)
        // TODO: Use the ViewModel
        setHasOptionsMenu(true)

        // TODO: あとで削除
        var db = Room.databaseBuilder(
            activity!!.applicationContext, AppDatabase::class.java, "database-name").build()

        // リストビューの作成
        layoutManager = LinearLayoutManager(activity)

        val pac = Pac(pacId = 0, content = "あああ", "")
        val pac2 = Pac(pacId = 1, content = "良いい", "")
        // viewAdapter = PacListViewAdapter(db.pacDao().getAll())
        viewAdapter = PacListViewAdapter(listOf(pac, pac2))
        viewAdapter.setOnItemClickListener(object: PacListViewAdapter.OnItemClickListener{
            override fun onItemClick(view: View, position: Int, clickedText: String) {
                Toast.makeText(activity!!.applicationContext, "${clickedText}がタップされました", Toast.LENGTH_LONG).show()
            }
        })
        recyclerView = view!!.findViewById<RecyclerView>(R.id.recyclerView).also {
            it.layoutManager = layoutManager
            it.adapter = viewAdapter
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

    override fun onItemClick(view: View, position: Int, clickedText: String) {
        println("テスト")
    }
}