@file:Suppress("unused")

package com.smartviser.androidext

import java.nio.ByteBuffer

fun Any.tag(): String = javaClass.simpleName

fun Int.nullIfMax(): Int? = if (this == Int.MAX_VALUE) null else this
fun Long.nullIfMax(): Long? = if (this == Long.MAX_VALUE) null else this

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
