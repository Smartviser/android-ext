@file:Suppress("unused")

package com.smartviser.androidext

import android.content.Context
import android.os.Environment
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStream
import java.math.BigInteger
import java.security.DigestInputStream
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
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

val File.md5: String?
    get() = try {
        val messageDigest = MessageDigest.getInstance("MD5")
        val inputStream = FileInputStream(this)
        val digestStream = DigestInputStream(inputStream, messageDigest)
        val buffer = ByteArray(8192)
        while (digestStream.read(buffer) > 0);
        val bigInt = BigInteger(1, messageDigest.digest())
        var output = bigInt.toString(16)
        output = String.format("%32s", output).replace(' ', '0')
        digestStream.close()
        inputStream.close()
        output
    } catch (e: NoSuchAlgorithmException) {
        null
    } catch (e: IOException) {
        null
    }
