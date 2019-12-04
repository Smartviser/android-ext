@file:Suppress("unused")

package com.smartviser.androidext

import android.app.ActivityManager
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.location.LocationManager
import android.media.AudioManager
import android.os.Build
import android.os.PowerManager
import android.provider.Telephony
import android.telecom.TelecomManager
import android.telephony.SubscriptionManager
import android.telephony.TelephonyManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog

val Context.telephonyManager: TelephonyManager
    get() = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

val Context.subscriptionManager: SubscriptionManager
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    get() = getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE) as SubscriptionManager

val Context.locationManager: LocationManager
    get() = getSystemService(Context.LOCATION_SERVICE) as LocationManager

val Context.telecomManager: TelecomManager
    get() = getSystemService(Context.TELECOM_SERVICE) as TelecomManager

val Context.activityManager: ActivityManager
    get() = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager

val Context.audioManager: AudioManager
    get() = getSystemService(Context.AUDIO_SERVICE) as AudioManager

val Context.powerManager: PowerManager
    get() = getSystemService(Context.POWER_SERVICE) as PowerManager

// Application BuildConfig
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

fun Context.getBuildConfigValue(fieldName: String): Any? {
    try {
        val clazz = Class.forName("$packageName.BuildConfig")
        val field = clazz.getField(fieldName)
        return field.get(null)
    } catch (e: ClassNotFoundException) {
        e.printStackTrace()
    } catch (e: NoSuchFieldException) {
        e.printStackTrace()
    } catch (e: IllegalAccessException) {
        e.printStackTrace()
    }

    return null
}

val Context.applicationVersion
    get() = "${getBuildConfigValue("VERSION_NAME")} (${getBuildConfigValue("VERSION_CODE")})"

// Resources
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

fun Context.getResourcesArrayValue(arrayId: Int, position: Int): String? =
    try {
        this.resources.getStringArray(arrayId)[position]
    } catch (e: Resources.NotFoundException) {
        null
    }

// Permissions
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

fun Context.checkPermission(vararg permissions: String): Boolean =
    Build.VERSION.SDK_INT < Build.VERSION_CODES.M || permissions.fold(true) { accumulator, permission ->
        accumulator && checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
    }

fun Context.isDefaultSmsApp() =
    packageName == Telephony.Sms.getDefaultSmsPackage(this)

fun Context.setDefaultSmsApp() {
    val intent = Intent(Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT)
    intent.putExtra(Telephony.Sms.Intents.EXTRA_PACKAGE_NAME, packageName)
    startActivity(intent)
}

@RequiresApi(Build.VERSION_CODES.M)
fun Context.isDefaultDialerApp() =
    packageName == telecomManager.defaultDialerPackage

@RequiresApi(Build.VERSION_CODES.M)
fun Context.setDefaultDialerApp() {
    val intent = Intent(TelecomManager.ACTION_CHANGE_DEFAULT_DIALER)
    intent.putExtra(TelecomManager.EXTRA_CHANGE_DEFAULT_DIALER_PACKAGE_NAME, packageName)
    startActivity(intent)
}

// Popups
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

fun Context.popup(titleId: Int?, messageId: Int) {
    popup(titleId, messageId, null)
}

fun Context.popup(
    titleId: Int?,
    messageId: Int,
    listener: DialogInterface.OnClickListener?
) {
    popup(titleId, getString(messageId), listener)
}

fun Context.popup(
    titleId: Int?,
    message: String,
    listener: DialogInterface.OnClickListener?
) {
    val builder = AlertDialog.Builder(this)
    if (titleId != null) {
        builder.setTitle(titleId)
    }
    builder.setCancelable(false)
    builder.setMessage(message)
    builder.setPositiveButton(getString(android.R.string.ok), listener)
    builder.create().show()
}

fun Context.waitPopup(): ProgressDialog =
    ProgressDialog.show(this, "Loading", "Please wait...", true)

fun Context.toast(message: String, isShort: Boolean) =
    Toast.makeText(this, message, if (isShort) Toast.LENGTH_SHORT else Toast.LENGTH_LONG).show()
