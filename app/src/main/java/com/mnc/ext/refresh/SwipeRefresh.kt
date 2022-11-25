package com.mnc.ext.refresh

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.mnc.ext.common.launchDelayedFunction

fun SwipeRefreshLayout.refresh() {
    isRefreshing = true
}

fun SwipeRefreshLayout.finish(delay: Long = 0) {
    launchDelayedFunction(delay) { isRefreshing = false }
}