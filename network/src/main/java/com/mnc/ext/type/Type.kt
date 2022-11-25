package com.mnc.ext.type

fun String?.toIntOrZero(): Int {
    return this?.filter { it.isDigit() }?.toIntOrNull() ?: 0
}

fun String?.toLongOrZero(): Long {
    return this?.filter { it.isDigit() }?.toLong() ?: 0
}

fun Boolean?.orTrue(): Boolean = this ?: true
fun Boolean?.orFalse(): Boolean = this ?: false

fun Int?.orZero(): Int = this ?: 0
fun Int?.orOne(): Int = this ?: 1

fun Double?.orZero(): Double = this ?: 0.0
fun Double?.orOne(): Double = this ?: 1.0

fun Long?.orZero(): Long = this ?: 0L
fun Long?.orOne(): Long = this ?: 1L

fun Float?.orZero(): Float = this ?: 0f
fun Float?.orOne(): Float = this ?: 1f