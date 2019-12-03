@file:Suppress("unused")

package com.smartviser.androidext

import android.content.Intent

fun Intent.listExtras(): String {
    var result = ""
    extras?.apply {
        for (key in this.keySet()) {
            val value = this.get(key)
            result += "\n - [$key] = $value (${value?.javaClass?.name ?: ""})"
        }
    }
    return result
}