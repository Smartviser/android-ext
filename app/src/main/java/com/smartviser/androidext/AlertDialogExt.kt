@file:Suppress("unused")

package com.smartviser.androidext

import android.app.AlertDialog
import android.content.Context

fun showErrorDialog(context: Context, message: String) {
    dialogBuilder(context, message).create()?.show()
}

private fun dialogBuilder(context: Context, message: String) =
    AlertDialog.Builder(context).apply {
        setMessage(message)
        setCancelable(false)
        setTitle(R.string.app_name)
        setNeutralButton("OK", null)
        setIcon(android.R.drawable.ic_dialog_alert)
    }
