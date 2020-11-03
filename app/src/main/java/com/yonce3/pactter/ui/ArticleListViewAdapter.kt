package com.yonce3.pactter.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yonce3.pactter.R
import com.yonce3.pactter.data.entity.Article

class ArticleListViewAdapter() : RecyclerView.Adapter<ArticleListViewAdapter.ArticleListViewHolder>() {

    var articles = emptyList<Article>()
    class ArticleListViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val articleImage = view.findViewById<ImageView>(R.id.author_icon)
        val articleTitle = view.findViewById<TextView>(R.id.qiita_title)
        val articleContent = view.findViewById<TextView>(R.id.qiita_content)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleListViewHolder {
        val lowView = LayoutInflater.from(parent.context).inflate(R.layout.qiita_list_item, parent, false)
        return ArticleListViewHolder(lowView)
    }

    override fun onBindViewHolder(holder: ArticleListViewHolder, position: Int) {
        holder.articleTitle.text = articles[position].title
        holder.articleContent.text = articles[position].context
    }

    override fun getItemCount(): Int = articles.size

    internal fun setArticles(articles: List<Article>) {
        this.articles = articles
        notifyDataSetChanged()
    }
}