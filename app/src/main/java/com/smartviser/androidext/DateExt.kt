@file:Suppress("unused")

package com.smartviser.androidext

import java.text.SimpleDateFormat
import java.util.*

fun Date.formatISO8601(includeTimezone: Boolean): String {
    val dateFormat = if (includeTimezone) "yyyy-MM-dd'T'HH:mm:ssZ" else "yyyy-MM-dd'T'HH:mm:ss"
    return SimpleDateFormat(dateFormat, Locale.US).format(this)
}

fun Date.toVwsFormat(): String {
    val dateFormat = "yyyy.MM.dd-HH.mm.ss"
    return SimpleDateFormat(dateFormat, Locale.US).format(this)
}

fun Date.format2445(): String {
    val dateFormat = "yyyyMMdd'T'HHmmss"
    return SimpleDateFormat(dateFormat, Locale.US).format(this)
}

fun Date.humanFormat(): String {
    val dateFormat = "yyyy.MM.dd-HH.mm.ss"
    return SimpleDateFormat(dateFormat, Locale.getDefault()).format(this)
}