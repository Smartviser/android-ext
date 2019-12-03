@file:Suppress("unused")

package com.smartviser.androidext

import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.os.Build
import android.provider.Telephony
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
