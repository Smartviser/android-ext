@file:Suppress("unused")

package com.smartviser.androidext

import java.nio.ByteBuffer
import java.util.concurrent.TimeUnit

fun Any.tag(): String = javaClass.simpleName

fun Int.nullIfMax(): Int? = if (this == Int.MAX_VALUE) null else this
fun Long.nullIfMax(): Long? = if (this == Long.MAX_VALUE) null else this

@Throws(IllegalArgumentException::class)
fun Long.durationBreakdown(): String {
    require(this >= 0) { "Duration must be greater than zero!" }

    var millis = this
    val days: Long = TimeUnit.MILLISECONDS.toDays(millis)
    millis -= TimeUnit.DAYS.toMillis(days)
    val hours: Long = TimeUnit.MILLISECONDS.toHours(millis)
    millis -= TimeUnit.HOURS.toMillis(hours)
    val minutes: Long = TimeUnit.MILLISECONDS.toMinutes(millis)
    millis -= TimeUnit.MINUTES.toMillis(minutes)
    val seconds: Long = TimeUnit.MILLISECONDS.toSeconds(millis)
    millis -= TimeUnit.SECONDS.toMillis(seconds)

    val sb = StringBuilder(64)
    if (days > 0) {
        sb.append(days)
        sb.append(" days ")
    }
    if (days + hours > 0) {
        sb.append(hours)
        sb.append(" hours ")
    }
    if (days + hours + minutes > 0) {
        sb.append(minutes)
        sb.append(" minutes ")
    }
    if (days + hours + minutes + seconds > 0) {
        sb.append(seconds)
        sb.append(" seconds ")
    }
    if (millis > 0) {
        sb.append(millis)
        sb.append(" ms")
    }

    return sb.toString().trim()
}


fun ByteArray.hexString(separator: String = ":"): String {
    val buf = StringBuilder()
    for (aMac in this) {
        buf.append(String.format("%02X$separator", aMac))
    }
    if (buf.isNotEmpty()) {
        buf.deleteCharAt(buf.length - 1)
    }
    return buf.toString()
}

fun Int.bytes(): ByteArray =
    ByteBuffer.allocate(4)
        .putInt(this)
        .array()

fun Long.bytes(): ByteArray =
    ByteBuffer.allocate(8)
        .putLong(this)
        .array()

fun Short.bytes(): ByteArray =
    ByteBuffer.allocate(2)
        .putShort(this)
        .array()

fun Double.bytes(): ByteArray =
    ByteBuffer.allocate(8)
        .putDouble(this)
        .array()

fun Float.bytes(): ByteArray =
    ByteBuffer.allocate(4)
        .putFloat(this)
        .array()

fun Char.bytes(): ByteArray =
    ByteBuffer.allocate(2)
        .putChar(this)
        .array()

fun Long.bytesToMegabytes(): Double =
    this / 1048576.0
