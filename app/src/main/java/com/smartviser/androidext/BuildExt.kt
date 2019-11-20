@file:Suppress("unused")

package com.smartviser.androidext

import android.os.Build

fun deviceFullName() =
    if (Build.MODEL.startsWith(Build.MANUFACTURER)) {
        Build.MODEL.capitalize()
    } else {
        Build.MANUFACTURER.capitalize() + " " + Build.MODEL.capitalize()
    }

const val applicationVersion = "${BuildConfig.VERSION_NAME} (${BuildConfig.VERSION_CODE})"
