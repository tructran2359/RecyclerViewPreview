package com.me.recyclerviewpreview

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.load(url: String?) {
    if (url.isNullOrEmpty()) {
        return
    }

    Glide.with(this)
            .load(url)
            .into(this)
}
