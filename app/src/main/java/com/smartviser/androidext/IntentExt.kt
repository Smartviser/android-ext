@file:Suppress("unused")

package com.smartviser.androidext

import android.content.Intent

fun Intent.listExtras(): String {
    var result = ""
    extras?.let { bundle ->
        for (key in bundle.keySet()) {
            val value = bundle.get(key)
            result += "\n - [$key] = $value (${value?.javaClass?.name ?: ""})"
        }
    }
    return result
}