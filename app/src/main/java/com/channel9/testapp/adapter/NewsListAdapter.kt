package com.channel9.testapp.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.channel9.testapp.model.News
import com.channel9.testapp.view.NewsItemView

/**
 * Adapter to load a list of [News] inside a RecyclerView in NewsListFragment
 */
class NewsListAdapter : ListAdapter<News, NewsListAdapter.NewsListViewHolder>(NewsDiffCallBack()) {

    class NewsListViewHolder(
        private val newsItemView: NewsItemView
    ) : RecyclerView.ViewHolder(newsItemView) {
        fun bind(news: News) {
            newsItemView.setData(news)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsListViewHolder =
        NewsListViewHolder(NewsItemView(parent.context))

    override fun onBindViewHolder(holder: NewsListViewHolder, position: Int) {
        val news = getItem(position)
        holder.bind(news)
    }

    private class NewsDiffCallBack : DiffUtil.ItemCallback<News>() {
        override fun areItemsTheSame(oldItem: News, newItem: News): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: News, newItem: News): Boolean = oldItem == newItem
    }
}