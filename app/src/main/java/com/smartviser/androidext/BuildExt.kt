@file:Suppress("unused")

package com.smartviser.androidext

import android.Manifest
import android.content.Context
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresPermission
import java.io.UnsupportedEncodingException
import java.util.*

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

@RequiresPermission(Manifest.permission.READ_PHONE_STATE)
fun Context.generateDeviceId(): UUID? =
    try {
        val androidId = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
        if ("9774d56d682e549c" != androidId) {
            UUID.nameUUIDFromBytes(androidId.toByteArray(charset("utf8")))
        } else {
            val deviceId = telephonyManager.deviceId
            if (deviceId.isNullOrEmpty()) {
                UUID.randomUUID()
            } else {
                UUID.nameUUIDFromBytes(deviceId.toByteArray(charset("utf8")))
            }
        }
    } catch (e: UnsupportedEncodingException) {
        UUID.randomUUID()
    }

private const val DEVICE_ID_PREFS_FILE = "device_id.xml"
private const val PREFS_DEVICE_ID = "device_id"

@RequiresPermission(Manifest.permission.READ_PHONE_STATE)
fun Context.storedDeviceId(): UUID? {
    val prefs = getSharedPreferences(DEVICE_ID_PREFS_FILE, 0)
    val uuidString: String? = prefs[PREFS_DEVICE_ID, null]
    if (uuidString != null) {
        // Use the ids previously computed and stored in the prefs file
        return UUID.fromString(uuidString)
    }

    val uuid = generateDeviceId()
    if (uuid != null) {
        prefs[PREFS_DEVICE_ID] = uuid.toString()
    }
    return uuid
}