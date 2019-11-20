@file:Suppress("unused")

package com.smartviser.androidext

import android.os.Environment
import java.io.File

const val MEDIA_FOLDER = "/ViserAux"

fun mediaDirectory() = File(Environment.getExternalStorageDirectory(), MEDIA_FOLDER)
fun mediaFile(fileName: String) = File(mediaDirectory(), fileName)
fun mediaFilePath(fileName: String) = mediaFile(fileName).absolutePath
