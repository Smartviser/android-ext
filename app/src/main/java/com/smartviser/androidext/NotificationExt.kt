@file:Suppress("unused")

package com.smartviser.androidext

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context

fun createNotificationChannel(context: Context, name: String, description: String? = null) {
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(notificationChannelId(), name, importance)
        channel.description = description ?: name
        // Register the channel with the system; you can't change the importance
        // or other notification behaviours after this
        val notificationManager = context.getSystemService(NotificationManager::class.java)
        notificationManager?.createNotificationChannel(channel)
    }
}

fun notificationChannelId() = "${BuildConfig.APPLICATION_ID}.CHANNEL_ID"

fun Notification.Builder.setDefaultChannelId() {
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
        setChannelId(notificationChannelId())
    }
}
