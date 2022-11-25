package com.mnc.network.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Transaction(val image: String? = null, val name: String? = null, val favorite: Boolean? = null) : Parcelable