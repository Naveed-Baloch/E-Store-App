package com.example.estore.utils

import android.widget.ImageView
import com.bumptech.glide.Glide


object ImageUtil {

    fun loadImageInto(imageUrl: String, imageView: ImageView) {
        Glide.with(imageView).load(imageUrl).into(imageView)
    }
}