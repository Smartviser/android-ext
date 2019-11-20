@file:Suppress("unused")

package com.smartviser.androidext

import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo

fun PackageManager.applicationPackageName(appName: String): String? {
    val installedPackages = this.getInstalledPackages(0)
    var packageName: String? = null

    for (packageData in installedPackages) {
        if (this.getApplicationLabel(packageData.applicationInfo) == appName) {
            packageName = packageData.packageName
            break
        }
    }
    return packageName
}

fun PackageManager.installedApplicationNames(): Array<String?> {
    val intent = Intent(Intent.ACTION_MAIN).apply {
        addCategory(Intent.CATEGORY_LAUNCHER)
    }
    val packages = ArrayList<ResolveInfo>(
        this.queryIntentActivities(intent, PackageManager.GET_META_DATA)
    )

    val appNames = arrayOfNulls<String>(packages.size)
    for ((i, resolveInfo) in packages.withIndex()) {
        appNames[i] = this.getApplicationLabel(resolveInfo.activityInfo.applicationInfo).toString()
    }
    return appNames
}
