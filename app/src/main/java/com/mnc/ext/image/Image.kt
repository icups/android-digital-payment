package com.mnc.ext.image

import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.mnc.app.R

fun AppCompatImageView.loadImage(url: String) {
    Glide.with(context)
        .load(url)
        .placeholder(android.R.color.transparent)
        .error(R.drawable.ic_baseline_broken_image_24)
        .into(this)
}

fun AppCompatImageView.loadImage(@DrawableRes resId: Int) {
    Glide.with(context)
        .load(ContextCompat.getDrawable(context, resId))
        .placeholder(android.R.color.transparent)
        .error(R.drawable.ic_baseline_broken_image_24)
        .into(this)
}

fun AppCompatImageView.loadCircleImage(url: String) {
    Glide.with(context)
        .load(url)
        .placeholder(android.R.color.transparent)
        .error(R.drawable.ic_baseline_broken_image_24)
        .apply(RequestOptions().circleCrop())
        .into(this)
}

fun AppCompatImageView.loadCircleImage(@DrawableRes resId: Int) {
    Glide.with(context)
        .load(ContextCompat.getDrawable(context, resId))
        .placeholder(android.R.color.transparent)
        .error(R.drawable.ic_baseline_broken_image_24)
        .apply(RequestOptions().circleCrop())
        .into(this)
}

fun AppCompatImageView.loadRoundedImage(url: String, radius: Int) {
    Glide.with(context)
        .load(url)
        .apply(RequestOptions.bitmapTransform(RoundedCorners(radius)))
        .placeholder(android.R.color.transparent)
        .error(R.drawable.ic_baseline_broken_image_24)
        .into(this)
}