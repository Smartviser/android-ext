@file:Suppress("unused")

package com.smartviser.androidext

import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo

fun PackageManager.applicationPackageName(applicationName: String): String? {
    val installedPackages = this.getInstalledPackages(0)
    var packageName: String? = null

    for (packageData in installedPackages) {
        if (this.getApplicationLabel(packageData.applicationInfo) == applicationName) {
            packageName = packageData.packageName
            break
        }
    }
    return packageName
}

fun PackageManager.installedApplicationNames(): List<String> {
    val intent = Intent(Intent.ACTION_MAIN).apply {
        addCategory(Intent.CATEGORY_LAUNCHER)
    }
    val packages = ArrayList<ResolveInfo>(
        this.queryIntentActivities(intent, PackageManager.GET_META_DATA)
    )

    return packages.map { this.getApplicationLabel(it.activityInfo.applicationInfo).toString() }
}
