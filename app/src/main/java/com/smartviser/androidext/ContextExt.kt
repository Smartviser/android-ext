@file:Suppress("unused")

package com.smartviser.androidext

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.telephony.SubscriptionManager
import android.telephony.TelephonyManager
import androidx.annotation.RequiresApi
import android.content.res.Resources
import android.widget.Toast

val Context.telephonyManager: TelephonyManager
    get() = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

val Context.subscriptionManager: SubscriptionManager
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    get() = getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE) as SubscriptionManager

fun Context.checkPermission(vararg permissions: String): Boolean =
    Build.VERSION.SDK_INT < Build.VERSION_CODES.M || permissions.fold(true) { accumulator, permission ->
        accumulator && checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
    }

fun Context.toast(message: String, isShort: Boolean) =
    Toast.makeText(this, message, if (isShort) Toast.LENGTH_SHORT else Toast.LENGTH_LONG).show()

fun Context.getResourcesArrayValue(arrayId: Int, position: Int): String? =
    try {
        this.resources.getStringArray(arrayId)[position]
    } catch (e: Resources.NotFoundException) {
        null
    }
