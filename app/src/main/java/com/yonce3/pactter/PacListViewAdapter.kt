package com.yonce3.pactter

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yonce3.pactter.data.entity.Pac

class PacListViewAdapter(val list: List<Pac>) : RecyclerView.Adapter<PacListViewAdapter.PacListViewHolder>() {

    // リスナー格納変数
    lateinit var listener: OnItemClickListener

    class PacListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val listItemContainer = view.findViewById<RelativeLayout>(R.id.list_item_container)
        val pacContent = view.findViewById<TextView>(R.id.contents)
    }


    override fun onBindViewHolder(holder: PacListViewHolder, position: Int) {
        holder.pacContent.text = list[position].content
        holder.listItemContainer.setOnClickListener { list[position].content }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PacListViewHolder {
        val rowView = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return PacListViewHolder(rowView)
    }

    override fun getItemCount(): Int = list.size

    //インターフェースの作成
    interface OnItemClickListener{
        fun onItemClick(view: View, position: Int, clickedText: String)
    }

    // リスナー
    fun setOnItemClickListener(listener: OnItemClickListener){
        this.listener = listener
    }
}