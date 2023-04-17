package com.channel9.testapp.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.channel9.testapp.R
import com.channel9.testapp.databinding.LayoutNewsItemBinding
import com.channel9.testapp.model.News

class NewsItemView(
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    private val binding: LayoutNewsItemBinding = DataBindingUtil.inflate(
        LayoutInflater.from(context),
        R.layout.layout_news_item,
        this,
        true
    )

    fun setData(news: News) {
        binding.apply {
            // Load the thumbnail if available otherwise hide the imageView
            if (news.isThumbnailUrlAvailable) {
                thumbnailImageView.apply {
                    visibility = View.VISIBLE
                    news.thumbnailUrl?.let { loadThumbnail(it) }
                }
            } else {
                thumbnailImageView.visibility = View.GONE
            }
            headlineTextView.text = news.headline
            theAbstractTextView.text = news.theAbstract
            byLineTextView.text = news.byLine
        }
    }

    private fun ImageView.loadThumbnail(url: String) {
        Glide.with(this)
            .load(url)
            .centerCrop()
            .into(this)
    }
}