@file:Suppress("unused")

package com.smartviser.androidext

import android.text.Editable

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
