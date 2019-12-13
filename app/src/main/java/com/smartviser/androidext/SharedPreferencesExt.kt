package com.smartviser.androidext

import android.content.SharedPreferences

private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
    val editor = this.edit()
    operation(editor)
    editor.apply()
}

operator fun SharedPreferences.get(key: String, default: Int) = getInt(key, default)
operator fun SharedPreferences.get(key: String, default: Long) = getLong(key, default)
operator fun SharedPreferences.get(key: String, default: Float) = getFloat(key, default)
operator fun SharedPreferences.get(key: String, default: Boolean) = getBoolean(key, default)
operator fun SharedPreferences.get(key: String, default: String?) : String? = getString(key, default)
operator fun SharedPreferences.get(key: String, default: Set<String>) : Set<String>? = getStringSet(key, default)

operator fun SharedPreferences.set(key: String, value: Int?) =
    if (value == null) {
        edit { it.remove(key) }
    } else {
        edit { it.putInt(key, value) }
    }
operator fun SharedPreferences.set(key: String, value: Long?) =
    if (value == null) {
        edit { it.remove(key) }
    } else {
        edit { it.putLong(key, value) }
    }
operator fun SharedPreferences.set(key: String, value: Float?) =
    if (value == null) {
        edit { it.remove(key) }
    } else {
        edit { it.putFloat(key, value) }
    }
operator fun SharedPreferences.set(key: String, value: Boolean?) =
    if (value == null) {
        edit { it.remove(key) }
    } else {
        edit { it.putBoolean(key, value) }
    }
operator fun SharedPreferences.set(key: String, value: String?) =
    if (value == null) {
        edit { it.remove(key) }
    } else {
        edit { it.putString(key, value) }
    }
operator fun SharedPreferences.set(key: String, value: Set<String>?) =
    if (value == null) {
        edit { it.remove(key) }
    } else {
        edit { it.putStringSet(key, value) }
    }

// Nicer but cannot support Set<String>
//operator fun SharedPreferences.set(key: String, value: Any?) {
//    if (value == null) {
//        edit().remove(key).apply()
//    } else {
//        when (value) {
//            is String? -> edit { it.putString(key, value) }
//            is Int -> edit { it.putInt(key, value) }
//            is Boolean -> edit { it.putBoolean(key, value) }
//            is Float -> edit { it.putFloat(key, value) }
//            is Long -> edit { it.putLong(key, value) }
//            else -> throw UnsupportedOperationException("Not yet implemented")
//        }
//    }
//}
//
//inline operator fun <reified T : Any> SharedPreferences.get(key: String, defaultValue: T): T? {
//    return when (T::class) {
//        String::class -> getString(key, defaultValue as String) as T?
//        Int::class -> getInt(key, defaultValue as Int) as T?
//        Boolean::class -> getBoolean(key, defaultValue as Boolean) as T?
//        Float::class -> getFloat(key, defaultValue as Float) as T?
//        Long::class -> getLong(key, defaultValue as Long) as T?
//        else -> throw UnsupportedOperationException("Not yet implemented")
//    }
//}
