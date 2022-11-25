package com.mnc.network.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PaginatedResponse<T : Parcelable>(
    val count: Int,
    val page: Int,
    val pageCount: Int,
    val size: String? = null,
    val recordCount: String? = null,
    @SerializedName("data") val results: List<T> = emptyList()
) : Parcelable {

    fun isLastPage(): Boolean {
        return pageCount <= page
    }

}