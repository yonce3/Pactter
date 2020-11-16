package com.yonce3.pactter.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.yonce3.pactter.R
import com.yonce3.pactter.data.entity.Pac

class PacListViewAdapter() : RecyclerView.Adapter<PacListViewAdapter.PacListViewHolder>() {

    // リスナー格納変数
    lateinit var listener: OnItemClickListener
    var pacs = emptyList<Pac>() // Cached copy of words

    class PacListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val pacContent = view.findViewById<TextView>(R.id.contents)
        val pacImage = view.findViewById<ImageView>(R.id.pac_image)
    }


    override fun onBindViewHolder(holder: PacListViewHolder, position: Int) {
        holder.pacContent.text = pacs[position].content
        holder.pacImage.load(pacs[position].imageFilePath)
        holder.itemView.setOnClickListener {
            listener.onItemClick(it, position, pacs[position].pacId)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PacListViewHolder {
        val rowView = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return PacListViewHolder(rowView)
    }

    override fun getItemCount(): Int = pacs.size

    //インターフェースの作成
    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int, pacId: Int)
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