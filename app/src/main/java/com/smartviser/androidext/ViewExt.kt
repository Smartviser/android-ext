@file:Suppress("unused")

package com.smartviser.androidext

import android.view.View

fun View.visibleOrGone(condition: Boolean) {
    this.visibility = if (condition) View.VISIBLE else View.GONE
}

fun View.visibleOrInvisible(condition: Boolean) {
    this.visibility = if (condition) View.VISIBLE else View.INVISIBLE
}
