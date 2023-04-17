package com.channel9.testapp.adapter

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("setViewVisibility")
fun View.setViewVisibility(isVisible: Boolean) {
    this.visibility = if (isVisible) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

@BindingAdapter("setImageFromUrl")
fun ImageView.setImageFromUrl(url: String?) {
    if (url.isNullOrEmpty().not()) {
        Glide.with(this)
            .load(url)
            .centerCrop()
            .into(this)
    }
}