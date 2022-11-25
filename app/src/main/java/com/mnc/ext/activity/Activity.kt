package com.mnc.ext.activity

import android.app.Activity
import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat

fun <T> Activity.start(activityClass: Class<T>, bundle: Bundle? = null, closeCurrent: Boolean = false) {
    startActivity(Intent(this, activityClass).apply { bundle?.run { putExtras(this) } }, bundle)
    if (closeCurrent) finishAfterTransition()
}

fun <T> Activity.freshStart(activityClass: Class<T>, bundle: Bundle? = null) {
    setResult(Activity.RESULT_CANCELED)
    startActivity(Intent(this, activityClass).apply { bundle?.run { putExtras(this) } }, bundle)
    finishAffinity()
}

fun Activity.freshStart(pkg: String, cls: String, bundle: Bundle? = null) {
    setResult(Activity.RESULT_CANCELED)
    startActivity(Intent().apply { component = ComponentName(pkg, "$pkg.$cls"); bundle?.run { putExtras(this) } }, bundle)
    finishAffinity()
}

fun Activity.getStringExtra(key: String): String? {
    return intent?.getStringExtra(key)
}

fun Activity.getStringExtraOrEmpty(key: String): String {
    return intent?.getStringExtra(key) ?: ""
}

fun Activity.getBooleanExtra(key: String, default: Boolean = false): Boolean {
    return intent?.getBooleanExtra(key, false) ?: default
}

fun Activity.transitionAnimBundle(view: View, transitionName: String): Bundle? {
    return ActivityOptionsCompat.makeSceneTransitionAnimation(this, view, transitionName).toBundle()
}

fun Activity.hideKeyboard(action: (() -> Unit)? = null) {
    val manager = ContextCompat.getSystemService(this, InputMethodManager::class.java)
    manager?.hideSoftInputFromWindow(window.decorView.rootView.windowToken, 0)
    action?.invoke()
}