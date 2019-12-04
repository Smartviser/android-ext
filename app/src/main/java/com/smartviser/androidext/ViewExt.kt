@file:Suppress("unused")

package com.smartviser.androidext

import android.view.View

val View.isVisible
    get() = this.visibility == View.VISIBLE

var View.isInvisible
    get() = this.visibility == View.INVISIBLE
    set(value) { this.visibility = if (value) View.INVISIBLE else View.VISIBLE }

var View.isGone
    get() = this.visibility == View.GONE
    set(value) { this.visibility = if (value) View.GONE else View.VISIBLE }
