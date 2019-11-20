@file:Suppress("unused")

package com.smartviser.androidext

@Throws(ClassNotFoundException::class)
fun String.toClass() = Class.forName(this)

@Throws(ClassNotFoundException::class, InstantiationException::class, IllegalAccessException::class)
fun String.toInstance() = this.toClass().newInstance()

fun String.getPath(): String {
    var directory = substringBeforeLast("/", "")
    if (directory.isNotEmpty()) {
        directory += "/"
    }
    return directory
}
