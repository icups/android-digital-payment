package com.mnc.app.shared

import android.content.Context
import com.mnc.app.R

class ResourceProvider(private val context: Context) {

    val timeout by lazy { context.getString(R.string.connection_timed_out) }
    val generalError by lazy { context.getString(R.string.general_error) }
    val networkError by lazy { context.getString(R.string.check_your_network) }

}