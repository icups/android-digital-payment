package com.mnc.network.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Meta(val code: Int, val status: Boolean, val message: String) : Parcelable