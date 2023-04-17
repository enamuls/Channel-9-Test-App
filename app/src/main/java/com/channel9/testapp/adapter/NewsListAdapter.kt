package com.channel9.testapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.channel9.testapp.databinding.NewsItemViewBinding
import com.channel9.testapp.model.News

class NewsListAdapter : RecyclerView.Adapter<NewsListAdapter.NewsListViewHolder>() {
    private val newsList: List<News> = emptyList()

    class NewsListViewHolder(
        private val binding: NewsItemViewBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(news: News) {
            binding.news = news
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = NewsItemViewBinding.inflate(inflater, parent, false)
        return NewsListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsListViewHolder, position: Int) {
        val news = newsList[position]
        holder.bind(news)
    }

    override fun getItemCount(): Int = newsList.size
}