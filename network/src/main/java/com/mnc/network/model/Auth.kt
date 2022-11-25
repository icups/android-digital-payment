package com.mnc.network.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Auth(
    val id: String? = null,
    val name: String? = null,
    val token: String? = null
) : Parcelable