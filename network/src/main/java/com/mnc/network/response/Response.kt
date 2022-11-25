package com.mnc.network.response

import android.os.Parcelable
import com.mnc.network.model.Meta
import com.mnc.network.model.State
import kotlinx.parcelize.Parcelize

@Parcelize
data class Response<T : Parcelable?>(
    val meta: Meta,
    val data: T? = null
) : Parcelable {

    private val success: Boolean get() = meta.code == 200

    // -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

    private val formattedErrorMessage: String
        get() = when (meta.code) {
            1043 -> "Invalid email address, e.g. info@github.com"
            1406 -> "Name is too long, maximum 100 characters"
            4417 -> "Invalid verification code"
            else -> meta.message
        }

    fun <T> requireState(data: T? = null, action: ((T) -> Unit)? = null): State<T> {
        return if (success) {
            data?.run { action?.invoke(this) }
            State.success(data)
        } else {
            State.error(formattedErrorMessage, errorCode = meta.code)
        }
    }

}