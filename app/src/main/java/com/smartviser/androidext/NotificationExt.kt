@file:Suppress("unused")

package com.smartviser.androidext

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context

const val VISER_CHANNEL_ID = "com.smartviser.CHANNEL_ID"

fun createViserNotificationChannel(context: Context, importance: Int = NotificationManager.IMPORTANCE_DEFAULT) {
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
        val channel = NotificationChannel(VISER_CHANNEL_ID, "SmartViser", importance)
        channel.description = "SmartViser"
        // Register the channel with the system; you can't change the importance
        // or other notification behaviours after this
        val notificationManager = context.getSystemService(
            NotificationManager::class.java
        )
        notificationManager!!.createNotificationChannel(channel)
    }
}
