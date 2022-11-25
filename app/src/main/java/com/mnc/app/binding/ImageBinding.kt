package com.mnc.app.binding

import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.mnc.ext.image.loadCircleImage
import com.mnc.ext.image.loadImage
import com.mnc.ext.image.loadRoundedImage
import com.mnc.ext.type.orZero

object ImageBinding {

    @JvmStatic
    @BindingAdapter("android:imageSource")
    fun AppCompatImageView.bindImageSource(url: String? = null) {
        if (!url.isNullOrEmpty()) loadImage(url)
    }

    @JvmStatic
    @BindingAdapter("android:roundedImage", "android:imageRadius", requireAll = false)
    fun AppCompatImageView.bindRoundedImage(url: String? = null, radius: Int? = null) {
        if (!url.isNullOrEmpty()) loadRoundedImage(url, radius.orZero())
    }

    @JvmStatic
    @BindingAdapter("android:imageResource")
    fun AppCompatImageView.bindImageResource(@DrawableRes resId: Int? = null) {
        if (resId != null) loadImage(resId)
    }

    @JvmStatic
    @BindingAdapter("android:circleImage")
    fun AppCompatImageView.bindCircleImage(url: String? = null) {
        if (!url.isNullOrEmpty()) loadCircleImage(url)
    }

}