package com.mnc.network.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.mnc.ext.formatter.defaultFormat
import com.mnc.network.R
import kotlinx.parcelize.Parcelize

@Parcelize
data class Finance(
    val cardId: String? = null,
    val cardIssuer: String? = null,
    val cardNumber: String? = null,
    val amount: Int? = null,
    val bankImage: String? = null
) : Parcelable {

    val formattedCardNumber: String get() = "**** **** **** ${cardNumber?.takeLast(4)}"
    val formattedBalance: String get() = "Rp ${amount.defaultFormat()}"

    // -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

    val cardImage: Int get() = if (cardIssuer.equals("mastercard")) R.drawable.ic_mastercard else R.drawable.ic_visa

}