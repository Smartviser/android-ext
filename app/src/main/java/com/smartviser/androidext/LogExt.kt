@file:Suppress("unused")

package com.smartviser.androidext

class Log {
    companion object {
        fun v(tag: String?, msg: String) {
            if (BuildConfig.DEBUG) {
                android.util.Log.v(tag, msg)
            }
        }

        fun v(tag: String?, msg: String?, tr: Throwable?) {
            if (BuildConfig.DEBUG) {
                android.util.Log.v(tag, msg, tr)
            }
        }

        fun d(tag: String?, msg: String) {
            if (BuildConfig.DEBUG) {
                android.util.Log.d(tag, msg)
            }
        }

        fun d(tag: String?, msg: String?, tr: Throwable?) {
            if (BuildConfig.DEBUG) {
                android.util.Log.d(tag, msg, tr)
            }
        }

        fun i(tag: String?, msg: String) {
            if (BuildConfig.DEBUG) {
                android.util.Log.i(tag, msg)
            }
        }

        fun i(tag: String?, msg: String?, tr: Throwable?) {
            if (BuildConfig.DEBUG) {
                android.util.Log.i(tag, msg, tr)
            }
        }

        fun w(tag: String?, msg: String) {
            if (BuildConfig.DEBUG) {
                android.util.Log.w(tag, msg)
            }
        }

        fun w(tag: String?, msg: String?, tr: Throwable?) {
            if (BuildConfig.DEBUG) {
                android.util.Log.w(tag, msg, tr)
            }
        }

        fun w(tag: String?, tr: Throwable?) {
            if (BuildConfig.DEBUG) {
                android.util.Log.w(tag, tr)
            }
        }

        fun e(tag: String?, msg: String) {
            if (BuildConfig.DEBUG) {
                android.util.Log.e(tag, msg)
            }
        }

        fun e(tag: String?, msg: String?, tr: Throwable?) {
            if (BuildConfig.DEBUG) {
                android.util.Log.e(tag, msg, tr)
            }
        }

        fun wtf(tag: String?, msg: String?) {
            if (BuildConfig.DEBUG) {
                android.util.Log.wtf(tag, msg)
            }
        }

        fun wtf(tag: String?, tr: Throwable) {
            if (BuildConfig.DEBUG) {
                android.util.Log.wtf(tag, tr)
            }
        }

        fun wtf(tag: String?, msg: String?, tr: Throwable?) {
            if (BuildConfig.DEBUG) {
                android.util.Log.wtf(tag, msg, tr)
            }
        }
    }
}