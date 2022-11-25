package com.mnc.ext.fragment

import android.app.Activity
import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.mnc.ext.type.orFalse

fun <T> Fragment.start(activityClass: Class<T>, bundle: Bundle? = null, closeCurrent: Boolean = false) {
    activity?.run {
        startActivity(Intent(this, activityClass).apply { bundle?.run { putExtras(this) } }, bundle)
        setResult(Activity.RESULT_CANCELED)
        if (closeCurrent) finishAfterTransition()
    }
}

fun <T> Fragment.freshStart(activityClass: Class<T>, bundle: Bundle? = null) {
    activity?.run {
        startActivity(Intent(this, activityClass).apply { bundle?.run { putExtras(this) } }, bundle)
        setResult(Activity.RESULT_CANCELED)
        finishAffinity()
    }
}

fun Fragment.freshStart(pkg: String, cls: String, bundle: Bundle? = null) {
    activity?.run {
        setResult(Activity.RESULT_CANCELED)

        val intent = Intent().apply { component = ComponentName(pkg, "$pkg.$cls"); bundle?.run { putExtras(this) } }
        startActivity(intent, bundle)
        finishAffinity()
    }
}

fun Fragment.getStringArgs(key: String): String? {
    return arguments?.getString(key, null)
}

fun Fragment.getStringArgsOrEmpty(key: String): String {
    return arguments?.getString(key, "").orEmpty()
}

fun Fragment.getBooleanArgs(key: String): Boolean {
    return arguments?.getBoolean(key, false).orFalse()
}