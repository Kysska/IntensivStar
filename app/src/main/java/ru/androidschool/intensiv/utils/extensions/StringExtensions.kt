package ru.androidschool.intensiv.utils.extensions

import java.text.SimpleDateFormat
import java.util.Locale

const val DATE_FORMAT = "yyyy-MM-dd"
const val FALLBACK_YEAR = "0000"

fun String?.parseYearFromDate(): String {
    val dateFormat = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
    return try {
        val date = this?.let { dateFormat.parse(it) }
        date?.let { SimpleDateFormat("yyyy", Locale.getDefault()).format(it) } ?: FALLBACK_YEAR
    } catch (e: Exception) {
        FALLBACK_YEAR
    }
}
