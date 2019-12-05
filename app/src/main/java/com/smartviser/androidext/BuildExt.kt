@file:Suppress("unused")

package com.smartviser.androidext

import android.Manifest
import android.os.Build
import androidx.annotation.RequiresPermission

const val ANDROID_EXT_LIB_VERSION = "${BuildConfig.VERSION_NAME} (${BuildConfig.VERSION_CODE})"

fun deviceFullName() =
    if (Build.MODEL.startsWith(Build.MANUFACTURER)) {
        Build.MODEL.capitalize()
    } else {
        Build.MANUFACTURER.capitalize() + " " + Build.MODEL.capitalize()
    }

@RequiresPermission(Manifest.permission.READ_PHONE_STATE)
fun deviceSerial(): String =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        Build.getSerial()
    } else {
        Build.SERIAL
    }
