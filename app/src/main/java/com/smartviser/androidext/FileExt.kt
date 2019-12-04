@file:Suppress("unused")

package com.smartviser.androidext

import android.content.Context
import android.os.Environment
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.util.zip.ZipFile

lateinit var MEDIA_FOLDER: String

fun mediaDirectory() = File(Environment.getExternalStorageDirectory(), MEDIA_FOLDER)
fun mediaFile(name: String) = File(mediaDirectory(), name)
fun mediaFile(directoryName: String, name: String) = File(mediaFile(directoryName), name)
fun mediaFilePath(fileName: String): String = mediaFile(fileName).absolutePath

fun makeMediaSubDirectory(name: String): Boolean {
    val directory = mediaFile(name)
    if (!directory.exists()) {
        return directory.mkdir()
    }
    return true
}

@Throws(IOException::class)
fun File.copyTo(destination: File) {
    inputStream().use { input ->
        input.copyTo(destination)
        input.close()
    }
}

@Throws(IOException::class)
fun InputStream.copyTo(destination: File) {
    destination.outputStream().use { output ->
        this.copyTo(output)
        output.close()
    }
}

@Throws(IOException::class)
fun inputStreamFromAsset(context: Context, name: String): InputStream = context.assets.open(name)

@Throws(IOException::class)
fun File.unzip() {
    ZipFile(this).use { zip ->
        zip.entries().asSequence().forEach { entry ->
            zip.getInputStream(entry).use { input ->
                File(this.parentFile, entry.name).outputStream().use { output ->
                    input.copyTo(output)
                    output.close()
                }
            }
        }
    }
}

fun fileExist(name: String): Boolean = mediaFile(name).exists()

fun createFile(name: String): File {
    val file = mediaFile(name)
    if (!file.exists()) {
        file.createNewFile()
    }
    return file
}

fun deleteFile(name: String): Boolean {
    val file = mediaFile(name)
    if (file.exists()) {
        return file.delete()
    }
    return false
}
