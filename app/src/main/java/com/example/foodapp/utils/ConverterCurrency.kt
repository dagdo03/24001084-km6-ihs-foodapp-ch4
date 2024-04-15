package com.example.foodapp.utils

import java.text.NumberFormat
import java.util.Locale


fun Double?.doubleToCurrency(language: String, country: String): String? {
    return try {
        val locale = Locale(language, country)
        val number = NumberFormat.getCurrencyInstance(locale)
        number.format(this).toString()
    } catch (e: Exception) {
        null
    }
}
fun Int?.intToCurrency(language: String, country: String): String? {
    return try {
        val locale = Locale(language, country)
        val number = NumberFormat.getCurrencyInstance(locale)
        number.format(this).toString()
    } catch (e: Exception) {
        null
    }
}

fun Double?.toIndonesianFormat() = this.doubleToCurrency("in", "ID")
fun Int?.toIndonesianFormat() = this.intToCurrency("in", "ID")

fun String?.currencyToDouble(language: String, country: String): Double? {
    return try {
        val localeID = Locale(language, country)
        val numberFormat = NumberFormat.getCurrencyInstance(localeID)
        val parsed = numberFormat.parse(this)
        parsed?.toDouble()
    } catch (e: Exception) {
        null
    }
}

fun String?.fromCurrencyToDouble(): Double? = this.currencyToDouble("in", "ID")
