package com.mnc.network.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Home(
    val cards: List<Finance>,
    val transactions: List<Transaction>,
    val promo: List<Promo>
) : Parcelable