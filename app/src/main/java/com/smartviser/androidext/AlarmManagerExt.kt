package com.smartviser.androidext

import android.app.AlarmManager
import android.app.AlarmManager.AlarmClockInfo
import android.app.PendingIntent
import android.os.Build

fun AlarmManager.wakeupIn(duration: Long, wakeupIntent: PendingIntent?) {
    wakeupAt(System.currentTimeMillis() + duration, wakeupIntent)
}

fun AlarmManager.wakeupAt(time: Long, wakeupIntent: PendingIntent?) {
    if ("OPPO" == Build.MANUFACTURER) { // This method is sharper for OPPO phones...
        val info = AlarmClockInfo(time, null)
        setAlarmClock(info, wakeupIntent)
    } else {
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> {
                setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, time, wakeupIntent)
            }
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP -> {
                val info = AlarmClockInfo(time, null)
                setAlarmClock(info, wakeupIntent)
            }
            else -> {
                setExact(AlarmManager.RTC_WAKEUP, time, wakeupIntent)
            }
        }
    }
}
