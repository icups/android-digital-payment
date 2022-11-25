package com.mnc.ext.alert

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

fun Activity.showSnackBar(message: String?) {
    window.decorView.rootView?.apply { message?.let { Snackbar.make(this, message, Snackbar.LENGTH_SHORT).show() } }
}

fun Fragment.showSnackBar(message: String?) {
    message?.let { activity?.showSnackBar(message) }
}

fun Context.showToast(message: String?, withAction: (() -> Unit)? = null) {
    withAction?.invoke()
    if (!message.isNullOrEmpty()) Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Fragment.showToast(message: String?, withAction: (() -> Unit)? = null) {
    withAction?.invoke()
    message?.let { context?.showToast(message) }
}