package com.yonce3.pactter

import android.content.Context
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yonce3.pactter.data.entity.Pac

class PacListViewAdapter(context: Context) : RecyclerView.Adapter<PacListViewAdapter.PacListViewHolder>() {

    // リスナー格納変数
    lateinit var listener: OnItemClickListener
    var pacs = emptyList<Pac>() // Cached copy of words

    class PacListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val listItemContainer = view.findViewById<RelativeLayout>(R.id.list_item_container)
        val pacContent = view.findViewById<TextView>(R.id.contents)
    }


    override fun onBindViewHolder(holder: PacListViewHolder, position: Int) {
        holder.pacContent.text = pacs[position].content
        holder.listItemContainer.setOnClickListener { pacs[position].content }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PacListViewHolder {
        val rowView = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return PacListViewHolder(rowView)
    }

    override fun getItemCount(): Int = pacs.size

    //インターフェースの作成
    interface OnItemClickListener{
        fun onItemClick(view: View, position: Int, clickedText: String)
    }

    // リスナー
    fun setOnItemClickListener(listener: OnItemClickListener){
        this.listener = listener
    }

    internal fun setPacs(pacs: List<Pac>) {
        this.pacs = pacs
        notifyDataSetChanged()
    }
}