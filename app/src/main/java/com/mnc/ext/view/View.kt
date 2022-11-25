package com.mnc.ext.view

import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.DecelerateInterpolator
import android.view.animation.RotateAnimation
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import com.mnc.app.R
import com.mnc.app.listener.AnimationListener

fun View?.gone() {
    this?.context?.run {
        if (visibility == View.VISIBLE) {
            startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_out))
            visibility = View.GONE
        }
    }
}

fun View?.hide() {
    this?.context?.run {
        if (visibility == View.VISIBLE) {
            startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_out))
            visibility = View.INVISIBLE
        }
    }
}

fun View?.hideAndReveal(vararg views: View) {
    this?.context?.run {
        if (visibility == View.VISIBLE) {
            val listener = object : AnimationListener() {
                override fun onAnimationEnd() {
                    visibility = View.INVISIBLE
                    views.forEach { it.reveal() }
                }
            }

            startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_out).apply {
                setAnimationListener(listener)
            })
        }
    }
}

fun View?.reveal() {
    this?.context?.run {
        if (visibility != View.VISIBLE) {
            visibility = View.VISIBLE
            startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in))
        }
    }
}

fun View?.revealAndHide(vararg views: View) {
    this?.context?.run {
        if (visibility != View.VISIBLE) {
            val listener = object : AnimationListener() {
                override fun onAnimationEnd() {
                    visibility = View.VISIBLE
                    views.forEach { it.hide() }
                }
            }

            startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in).apply {
                setAnimationListener(listener)
            })
        }
    }
}

fun View.hideKeyboard() {
    context?.run {
        val manager = ContextCompat.getSystemService(this, InputMethodManager::class.java)
        manager?.hideSoftInputFromWindow(windowToken, 0)
    }
}

fun View.rotate(fromDegrees: Float, toDegrees: Float) {
    val rotation = RotateAnimation(fromDegrees, toDegrees, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f).apply {
        interpolator = DecelerateInterpolator()
        repeatCount = 0
        duration = 500
        fillAfter = true
    }

    startAnimation(rotation)
}

fun View.makeMeasureSpec() {
    measure(
        View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY),
        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
    )
}

inline fun <reified T : ViewGroup.LayoutParams> View.doOnLayoutWithHeight(view: View) {
    view.makeMeasureSpec()
    doOnLayoutWithHeight<T>(view.measuredHeight)
}

inline fun <reified T : ViewGroup.LayoutParams> View.doOnLayoutWithHeight(newMeasuredHeight: Int) {
    if (layoutParams.height != measuredHeight) {
        layoutParams = (layoutParams as T).also { lp -> lp.height = newMeasuredHeight }
    }
}