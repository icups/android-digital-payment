package com.mnc.network.payload

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
open class Payload(val email: String? = null, val password: String? = null) : Parcelable