package com.mnc.network.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Promo(
    val image: String? = null,
    val title: String? = null,
    val description: String? = null
) : Parcelable