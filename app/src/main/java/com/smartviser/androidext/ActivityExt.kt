@file:Suppress("unused")

package com.smartviser.androidext

import android.annotation.TargetApi
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.app.ProgressDialog
import android.content.Intent
import android.provider.Telephony
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction

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

// Popups
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

fun AppCompatActivity.popup(titleId: Int?, messageId: Int) {
    popup(titleId, messageId, null)
}

fun AppCompatActivity.popup(
    titleId: Int?,
    messageId: Int,
    listener: DialogInterface.OnClickListener?
) {
    val builder = AlertDialog.Builder(this)
    if (titleId != null) {
        builder.setTitle(titleId)
    }
    builder.setCancelable(false)
    builder.setMessage(messageId)
    builder.setPositiveButton("OK", listener)
    builder.create().show()
}

fun AppCompatActivity.waitPopup(): ProgressDialog =
    ProgressDialog.show(this, "Loading", "Please wait...", true)

// SMS application
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

fun AppCompatActivity.isDefaultSmsApp() =
    packageName == Telephony.Sms.getDefaultSmsPackage(this)

fun AppCompatActivity.setDefaultSmsApp() {
    val intent = Intent(Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT)
    intent.putExtra(Telephony.Sms.Intents.EXTRA_PACKAGE_NAME, packageName)
    startActivity(intent)
}

// Navigation
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

const val FRAGMENT_TAG = BuildConfig.APPLICATION_ID + ".FRAGMENT_TAG"

fun AppCompatActivity.replaceFragment(fragment: Fragment, titleId: Int) {
    val fragmentManager = supportFragmentManager

    val currentFragment = fragmentManager.findFragmentByTag(FRAGMENT_TAG)
    if (currentFragment != null && fragment.javaClass == currentFragment.javaClass) {
        return
    }

    // TODO: remove, we do not want to call specific layouts in this library
    fragmentManager.beginTransaction()
        .replace(R.id.containerLayout, fragment, FRAGMENT_TAG)
        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        .commit()

    val actionBar = supportActionBar
    actionBar?.setTitle(titleId)
}

fun AppCompatActivity.currentFragment() = supportFragmentManager.findFragmentByTag(FRAGMENT_TAG)