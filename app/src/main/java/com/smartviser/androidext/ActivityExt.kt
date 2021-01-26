@file:Suppress("unused")

package com.smartviser.androidext

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction

// Permissions
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

const val GLOBAL_PERMISSION_REQUEST_CODE = 1234

private val permissionSdkVersions = mapOf(
    Pair(android.Manifest.permission.ACCEPT_HANDOVER, Build.VERSION_CODES.P),
    Pair(android.Manifest.permission.ACCESS_BACKGROUND_LOCATION, Build.VERSION_CODES.Q),
    Pair(android.Manifest.permission.ACCESS_MEDIA_LOCATION, Build.VERSION_CODES.Q),
    Pair(android.Manifest.permission.ACCESS_NOTIFICATION_POLICY, Build.VERSION_CODES.M),
    Pair(android.Manifest.permission.ACCOUNT_MANAGER, Build.VERSION_CODES.ECLAIR),
    Pair(android.Manifest.permission.ACTIVITY_RECOGNITION, Build.VERSION_CODES.Q),
    Pair(android.Manifest.permission.ADD_VOICEMAIL, Build.VERSION_CODES.ICE_CREAM_SANDWICH),
    Pair(android.Manifest.permission.ANSWER_PHONE_CALLS, Build.VERSION_CODES.O),
    Pair(android.Manifest.permission.BIND_ACCESSIBILITY_SERVICE, Build.VERSION_CODES.JELLY_BEAN),
    Pair(android.Manifest.permission.BIND_APPWIDGET, Build.VERSION_CODES.CUPCAKE),
    Pair(android.Manifest.permission.BIND_AUTOFILL_SERVICE, Build.VERSION_CODES.O),
    Pair(android.Manifest.permission.BIND_CALL_REDIRECTION_SERVICE, Build.VERSION_CODES.Q),
    Pair(android.Manifest.permission.BIND_CARRIER_MESSAGING_CLIENT_SERVICE, Build.VERSION_CODES.Q),
    Pair(
        android.Manifest.permission.BIND_CARRIER_MESSAGING_SERVICE,
        Build.VERSION_CODES.LOLLIPOP_MR1
    ),
    Pair(android.Manifest.permission.BIND_CARRIER_SERVICES, Build.VERSION_CODES.M),
    Pair(android.Manifest.permission.BIND_CHOOSER_TARGET_SERVICE, Build.VERSION_CODES.M),
    Pair(android.Manifest.permission.BIND_CONDITION_PROVIDER_SERVICE, Build.VERSION_CODES.N),
    Pair(android.Manifest.permission.BIND_DEVICE_ADMIN, Build.VERSION_CODES.FROYO),
    Pair(android.Manifest.permission.BIND_DREAM_SERVICE, Build.VERSION_CODES.LOLLIPOP),
    Pair(android.Manifest.permission.BIND_INCALL_SERVICE, Build.VERSION_CODES.M),
    Pair(android.Manifest.permission.BIND_INPUT_METHOD, Build.VERSION_CODES.CUPCAKE),
    Pair(android.Manifest.permission.BIND_MIDI_DEVICE_SERVICE, Build.VERSION_CODES.M),
    Pair(android.Manifest.permission.BIND_NFC_SERVICE, Build.VERSION_CODES.KITKAT),
    Pair(
        android.Manifest.permission.BIND_NOTIFICATION_LISTENER_SERVICE,
        Build.VERSION_CODES.JELLY_BEAN_MR2
    ),
    Pair(android.Manifest.permission.BIND_PRINT_SERVICE, Build.VERSION_CODES.KITKAT),
    Pair(android.Manifest.permission.BIND_QUICK_SETTINGS_TILE, Build.VERSION_CODES.N),
    Pair(android.Manifest.permission.BIND_REMOTEVIEWS, Build.VERSION_CODES.HONEYCOMB),
    Pair(android.Manifest.permission.BIND_SCREENING_SERVICE, Build.VERSION_CODES.N),
    Pair(android.Manifest.permission.BIND_TELECOM_CONNECTION_SERVICE, Build.VERSION_CODES.M),
    Pair(android.Manifest.permission.BIND_TEXT_SERVICE, Build.VERSION_CODES.ICE_CREAM_SANDWICH),
    Pair(android.Manifest.permission.BIND_TV_INPUT, Build.VERSION_CODES.LOLLIPOP),
    Pair(android.Manifest.permission.BIND_VISUAL_VOICEMAIL_SERVICE, Build.VERSION_CODES.O),
    Pair(android.Manifest.permission.BIND_VOICE_INTERACTION, Build.VERSION_CODES.LOLLIPOP),
    Pair(android.Manifest.permission.BIND_VPN_SERVICE, Build.VERSION_CODES.ICE_CREAM_SANDWICH),
    Pair(android.Manifest.permission.BIND_VR_LISTENER_SERVICE, Build.VERSION_CODES.N),
    Pair(android.Manifest.permission.BIND_WALLPAPER, Build.VERSION_CODES.FROYO),
    Pair(android.Manifest.permission.BLUETOOTH_PRIVILEGED, Build.VERSION_CODES.KITKAT),
    Pair(android.Manifest.permission.BODY_SENSORS, Build.VERSION_CODES.KITKAT_WATCH),
    Pair(android.Manifest.permission.BROADCAST_SMS, Build.VERSION_CODES.BASE_1_1),
    Pair(android.Manifest.permission.BROADCAST_WAP_PUSH, Build.VERSION_CODES.BASE_1_1),
    Pair(android.Manifest.permission.CALL_COMPANION_APP, Build.VERSION_CODES.Q),
    Pair(android.Manifest.permission.CAPTURE_AUDIO_OUTPUT, Build.VERSION_CODES.KITKAT),
    Pair(android.Manifest.permission.CHANGE_WIFI_MULTICAST_STATE, Build.VERSION_CODES.DONUT),
    Pair(android.Manifest.permission.FOREGROUND_SERVICE, Build.VERSION_CODES.P),
    Pair(android.Manifest.permission.GET_ACCOUNTS_PRIVILEGED, Build.VERSION_CODES.M),
    Pair(android.Manifest.permission.GLOBAL_SEARCH, Build.VERSION_CODES.DONUT),
    Pair(android.Manifest.permission.INSTALL_LOCATION_PROVIDER, Build.VERSION_CODES.DONUT),
    Pair(android.Manifest.permission.INSTALL_SHORTCUT, Build.VERSION_CODES.KITKAT),
    Pair(android.Manifest.permission.INSTANT_APP_FOREGROUND_SERVICE, Build.VERSION_CODES.O),
    Pair(android.Manifest.permission.KILL_BACKGROUND_PROCESSES, Build.VERSION_CODES.FROYO),
    Pair(android.Manifest.permission.LOCATION_HARDWARE, Build.VERSION_CODES.JELLY_BEAN_MR2),
    Pair(android.Manifest.permission.MANAGE_DOCUMENTS, Build.VERSION_CODES.KITKAT),
    Pair(android.Manifest.permission.MANAGE_OWN_CALLS, Build.VERSION_CODES.O),
    Pair(android.Manifest.permission.MEDIA_CONTENT_CONTROL, Build.VERSION_CODES.KITKAT),
    Pair(android.Manifest.permission.MOUNT_FORMAT_FILESYSTEMS, Build.VERSION_CODES.CUPCAKE),
    Pair(android.Manifest.permission.NFC, Build.VERSION_CODES.GINGERBREAD),
    Pair(android.Manifest.permission.NFC_TRANSACTION_EVENT, Build.VERSION_CODES.P),
    Pair(android.Manifest.permission.PACKAGE_USAGE_STATS, Build.VERSION_CODES.M),
    Pair(android.Manifest.permission.READ_CALL_LOG, Build.VERSION_CODES.JELLY_BEAN),
    Pair(android.Manifest.permission.READ_EXTERNAL_STORAGE, Build.VERSION_CODES.JELLY_BEAN),
    Pair(android.Manifest.permission.READ_PHONE_NUMBERS, Build.VERSION_CODES.O),
    Pair(android.Manifest.permission.READ_VOICEMAIL, Build.VERSION_CODES.LOLLIPOP),
    Pair(android.Manifest.permission.REQUEST_COMPANION_RUN_IN_BACKGROUND, Build.VERSION_CODES.O),
    Pair(
        android.Manifest.permission.REQUEST_COMPANION_USE_DATA_IN_BACKGROUND,
        Build.VERSION_CODES.O
    ),
    Pair(android.Manifest.permission.REQUEST_DELETE_PACKAGES, Build.VERSION_CODES.O),
    Pair(android.Manifest.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS, Build.VERSION_CODES.M),
    Pair(android.Manifest.permission.REQUEST_INSTALL_PACKAGES, Build.VERSION_CODES.M),
    Pair(android.Manifest.permission.REQUEST_PASSWORD_COMPLEXITY, Build.VERSION_CODES.Q),
    Pair(android.Manifest.permission.SEND_RESPOND_VIA_MESSAGE, Build.VERSION_CODES.JELLY_BEAN_MR2),
    Pair(android.Manifest.permission.SET_ALARM, Build.VERSION_CODES.GINGERBREAD),
    Pair(android.Manifest.permission.SET_TIME, Build.VERSION_CODES.FROYO),
    Pair(android.Manifest.permission.START_VIEW_PERMISSION_USAGE, Build.VERSION_CODES.Q),
    Pair(android.Manifest.permission.TRANSMIT_IR, Build.VERSION_CODES.KITKAT),
    Pair(android.Manifest.permission.UNINSTALL_SHORTCUT, Build.VERSION_CODES.KITKAT),
    Pair(android.Manifest.permission.UPDATE_DEVICE_STATS, Build.VERSION_CODES.CUPCAKE),
    Pair(android.Manifest.permission.USE_BIOMETRIC, Build.VERSION_CODES.P),
    Pair(android.Manifest.permission.USE_FINGERPRINT, Build.VERSION_CODES.M),
    Pair(android.Manifest.permission.USE_FULL_SCREEN_INTENT, Build.VERSION_CODES.Q),
    Pair(android.Manifest.permission.USE_SIP, Build.VERSION_CODES.GINGERBREAD),
    Pair(android.Manifest.permission.WRITE_CALL_LOG, Build.VERSION_CODES.JELLY_BEAN),
    Pair(android.Manifest.permission.WRITE_EXTERNAL_STORAGE, Build.VERSION_CODES.DONUT),
    Pair(android.Manifest.permission.WRITE_SECURE_SETTINGS, Build.VERSION_CODES.CUPCAKE),
    Pair(android.Manifest.permission.WRITE_VOICEMAIL, Build.VERSION_CODES.LOLLIPOP)
)

fun AppCompatActivity.requestAllPermissions(): Boolean {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val permissions = neededPermissions()
        if (permissions.isNotEmpty()) {
            requestPermissions(permissions.toTypedArray(), GLOBAL_PERMISSION_REQUEST_CODE)
        }
        return permissions.isEmpty()
    }

    return true
}

private fun AppCompatActivity.neededPermissions(): List<String> {
    val permissions = mutableListOf<String>()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        try {
            val info = packageManager.getPackageInfo(packageName, PackageManager.GET_PERMISSIONS)
            if (info.requestedPermissions != null) {
                for (permission in info.requestedPermissions) {
                    // Needed for WIKO
                    if (!permission.startsWith("android.permission")) {
                        continue
                    }
                    if ((permissionSdkVersions[permission]
                            ?: Build.VERSION_CODES.BASE) > Build.VERSION.SDK_INT
                    ) {
                        continue
                    }

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        if(permission == Manifest.permission.ACCESS_BACKGROUND_LOCATION) {
                            continue;
                        }
                    }

                    if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                        permissions.add(permission)
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    return permissions
}

fun allGranted(permissions: Array<String>, grantResults: IntArray, activity: AppCompatActivity) =
    grantResults.foldIndexed(true) { index, acc, result ->
        acc && (result == PackageManager.PERMISSION_GRANTED ||
                (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                        && !ActivityCompat.shouldShowRequestPermissionRationale(activity, permissions[index])))
    }

// Navigation
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

const val FRAGMENT_TAG = "com.smartviser.android.FRAGMENT_TAG"

fun AppCompatActivity.replaceFragment(containerViewId: Int, fragment: Fragment, titleId: Int) {
    val fragmentManager = supportFragmentManager

    val currentFragment = fragmentManager.findFragmentByTag(FRAGMENT_TAG)
    if (currentFragment != null && fragment.javaClass == currentFragment.javaClass) {
        return
    }

    fragmentManager.beginTransaction()
        .replace(containerViewId, fragment, FRAGMENT_TAG)
        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        .commit()

    val actionBar = supportActionBar
    actionBar?.setTitle(titleId)
}

fun AppCompatActivity.currentFragment() = supportFragmentManager.findFragmentByTag(FRAGMENT_TAG)
