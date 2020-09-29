package com.yonce3.pactter

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yonce3.pactter.data.entity.Pac

class PacListViewAdapter(val list: List<Pac>) : RecyclerView.Adapter<PacListViewAdapter.PacListViewHolder>() {

    class PacListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    }


    override fun onBindViewHolder(holder: PacListViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PacListViewHolder {
        val rowView = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return PacListViewHolder(rowView)
    }

    override fun getItemCount(): Int = list.size
}