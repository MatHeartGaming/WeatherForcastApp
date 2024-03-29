package it.spaarkly.jetweatherforecastapp.util

import java.text.SimpleDateFormat
import java.util.*

fun formatDate(timestamp: Int): String {
    val sdf = SimpleDateFormat("EEE, MMM d", Locale.ITALY)
    val date = Date(timestamp.toLong() * 1000)

    return sdf.format(date)
}

fun formatDateTime(timestamp: Int): String {
    val sdf = SimpleDateFormat("hh:mm:aa", Locale.ITALY)
    val date = Date(timestamp.toLong() * 1000)

    return sdf.format(date)
}

fun formatDecimals(item: Double): String {
    return " %.0f".format(item)
}