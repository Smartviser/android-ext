package com.smartviser.androidext

import android.os.Bundle

val Bundle.allKeyValues: Map<String, Any?>
    get() = keySet().map { it to get(it) }.toMap()
