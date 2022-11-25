package com.mnc.ext.formatter

import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Base64
import com.mnc.ext.common.applyIf
import java.nio.charset.StandardCharsets
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.floor
import kotlin.math.log10
import kotlin.math.pow

fun Number?.defaultFormat(): String {
    return if (this == null) "" else NumberFormat.getNumberInstance().format(this)
}

fun Number?.prettyNumber(): String {
    if (this == null) return ""

    val suffix = charArrayOf(' ', 'K', 'M', 'B', 'T', 'P', 'E')
    val numValue = toLong()
    val value = floor(log10(numValue.toDouble())).toInt()
    val base = value / 3

    val formatter = DecimalFormat("#.#")
    return if (value >= 3 && base < suffix.size) {
        formatter.format(numValue / 10.0.pow((base * 3).toDouble())) + suffix[base]
    } else {
        formatter.format(toDouble())
    }
}

fun Number?.s(): String {
    return if (this == 1) "" else "s"
}

fun Number.addLeadingZero(): String {
    return String.format("%02d", this)
}

fun String.color(color: Int, start: Int = 0, end: Int = this.length): Spannable {
    val span = SpannableString(this)
    span.setSpan(
        ForegroundColorSpan(color),
        start,
        end,
        Spannable.SPAN_EXCLUSIVE_INCLUSIVE
    )
    return span
}

fun String.parseToUTCDate(pattern: String = "yyyy-MM-dd HH:mm:ss"): Date? {
    if (!"0001-01-01 00:00:00".equals(this, ignoreCase = true)) {
        try {
            return SimpleDateFormat(pattern, Locale.getDefault()).apply {
                timeZone = TimeZone.getTimeZone("UTC")
            }.parse(this)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }
    return null
}

fun String.parseToLocalDate(pattern: String = "yyyy-MM-dd HH:mm:ss"): Date? {
    if (!"0001-01-01 00:00:00".equals(this, ignoreCase = true)) {
        try {
            val simpleDateFormat = SimpleDateFormat(pattern, Locale.getDefault())
            return simpleDateFormat.parse(this)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }
    return null
}

fun String?.removeFirstIfEqualsTo(param: String): String {
    val data = this.orEmpty()
    return if (this.isNullOrEmpty()) "" else if (first().toString() == param) data.replaceFirst(param, "") else data
}

fun String.removeNumeric(): String {
    return try {
        replace("[0-9]+".toRegex(), "")
    } catch (e: Exception) {
        e.printStackTrace()
        ""
    }
}

fun String.removeSpecialCharacter(): String {
    return try {
        replace("[^a-zA-Z0-9 ]".toRegex(), "")
    } catch (e: Exception) {
        e.printStackTrace()
        ""
    }
}

fun String?.decode(): String {
    return if (this == null) "" else String(Base64.decode(this, Base64.DEFAULT), StandardCharsets.UTF_8)
}

fun Date?.formatDate(toPattern: String = "MMMM dd, yyyy", utc: Boolean = true): String {
    return if (this == null) "" else try {
        val formatter = SimpleDateFormat(toPattern, Locale.getDefault()).applyIf(utc) { timeZone = TimeZone.getTimeZone("UTC") }
        formatter.format(this)
    } catch (e: Exception) {
        e.printStackTrace()
        ""
    }
}

fun String?.formatDate(fromPattern: String = "yyyy-MM-dd hh:mm:ss", toPattern: String = "MMMM dd, yyyy", utc: Boolean = true): String {
    return if (isNullOrEmpty()) "" else try {
        var formatter = SimpleDateFormat(fromPattern, Locale.getDefault()).applyIf(utc) { timeZone = TimeZone.getTimeZone("UTC") }
        val date: Date? = formatter.parse(this)

        formatter = SimpleDateFormat(toPattern, Locale.getDefault())
        date?.run { formatter.format(this) }.orEmpty()
    } catch (e: Exception) {
        e.printStackTrace()
        ""
    }
}