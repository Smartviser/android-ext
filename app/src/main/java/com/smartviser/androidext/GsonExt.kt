@file:Suppress("unused")

package com.smartviser.androidext

import com.google.gson.reflect.TypeToken

inline fun <reified T> genericTypeToken() = object : TypeToken<T>() {}.type
