@file:Suppress("unused")

package com.smartviser.androidext

import android.content.Context
import android.os.Environment
import android.util.Log
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import java.io.*
import java.util.zip.ZipFile

// TODO: to remove
const val MEDIA_FOLDER = "/ViserReplay"
const val JSON_EXTENSION = ".json"

fun mediaDirectory() = File(Environment.getExternalStorageDirectory(), MEDIA_FOLDER)
fun mediaFile(name: String) = File(mediaDirectory(), name)
fun mediaFile(directoryName: String, name: String) = File(mediaFile(directoryName), name)

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
    Log.d("TOTO", "parent ${this.parentFile}")
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

fun getMediaDirectoryFileNames(): ArrayList<String> {
    val files = mediaDirectory().listFiles()
    if (files === null) {
        return ArrayList()
    }
    return ArrayList(files.map { f -> f.name.replace(JSON_EXTENSION, "") })
}

// TODO: to remove
fun getSequenceFileContent(name: String): JsonElement {
    val file = mediaFile(name + JSON_EXTENSION)
    if (!file.exists()) {
        throw FileNotFoundException("Could not find the file: ${name + JSON_EXTENSION}")
    }
    return JsonParser().parse(FileReader(file))
}

fun getSequenceFileName(name: String): String = name + JSON_EXTENSION
