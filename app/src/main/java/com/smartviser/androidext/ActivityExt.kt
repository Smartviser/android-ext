@file:Suppress("unused")

package com.smartviser.androidext

import android.annotation.TargetApi
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity

// Permissions
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

const val GLOBAL_PERMISSION_REQUEST_CODE = 1234

// TODO: use TargetApi in this case ?
@TargetApi(23)
fun AppCompatActivity.requestAllPermissions(): Boolean {
    val permissions = neededPermissions()
    if (permissions.isNotEmpty()) {
        requestPermissions(neededPermissions().toTypedArray(), GLOBAL_PERMISSION_REQUEST_CODE)
    }
    return permissions.isEmpty()
}

@TargetApi(23)
private fun AppCompatActivity.neededPermissions(): List<String> {
    val permissions = mutableListOf<String>()
    try {
        val info = packageManager.getPackageInfo(packageName, PackageManager.GET_PERMISSIONS)
        if (info.requestedPermissions != null) {
            for (permission in info.requestedPermissions) {
                // Needed for WIKO
                if (!permission.startsWith("android.permission")) {
                    continue
                }
                if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                    permissions.add(permission)
                }
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return permissions
}

fun allGranted(grantResults: IntArray) =
    grantResults.fold(true) { acc, result -> acc && (result == PackageManager.PERMISSION_GRANTED) }

