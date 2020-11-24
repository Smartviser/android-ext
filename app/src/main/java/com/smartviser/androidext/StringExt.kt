@file:Suppress("unused")

package com.smartviser.androidext

import android.text.Editable
import org.json.JSONException
import org.json.JSONObject
import java.security.MessageDigest

@Throws(ClassNotFoundException::class)
fun String.toClass(): Class<*> = Class.forName(this)

@Throws(ClassNotFoundException::class, InstantiationException::class, IllegalAccessException::class)
fun String.toInstance(): Any = this.toClass().newInstance()

fun String.getPath(): String {
    var directory = substringBeforeLast("/", "")
    if (directory.isNotEmpty()) {
        directory += "/"
    }
    return directory
}

fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)

fun String.appendListItem(separator: String, item: String): String {
    var result = this
    if (isNotEmpty()) result += separator
    result += item
    return result
}

val String.isNumeric: Boolean
    get() = matches("-?\\d+(\\.\\d+)?".toRegex())

val String.isInteger: Boolean
    get() = matches("-?\\d+".toRegex())

val String.isNatural: Boolean
    get() = matches("\\d+".toRegex())

val String.parsedJsonString: String?
    get() = try {
        JSONObject(this).toString()
    } catch (e: JSONException) {
        null
    }

val String.md5: String?
    get() {
        val bytes = MessageDigest.getInstance("MD5").digest(this.toByteArray())
        return bytes.joinToString("") {
            "%02x".format(it)
        }
    }