package com.mnc.ext.log

import android.util.Log
import com.mnc.app.BuildConfig

fun logInfo(message: String? = null, contextName: String? = null) {
    if (BuildConfig.DEBUG) {
        val section = if (contextName.isNullOrEmpty()) "" else "$contextName: "
        Log.i(BuildConfig.BUILD_TYPE, "$section$message")
    }
}

fun logError(e: Exception? = null, contextName: String? = null) {
    if (BuildConfig.DEBUG) {
        val section = if (contextName.isNullOrEmpty()) "" else "$contextName: "
        Log.e(BuildConfig.BUILD_TYPE, "$section${e?.message}")
        e?.printStackTrace()
    }
}