@file:Suppress("unused")

package com.smartviser.androidext

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ShapeDrawable

fun Drawable.setColor(color: Int) {
    when (this) {
        is ShapeDrawable -> paint.color = color
        is GradientDrawable -> setColor(color)
        is ColorDrawable -> this.color = color
    }
}
