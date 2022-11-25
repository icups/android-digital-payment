package com.mnc.app.shared

import android.content.Context
import android.content.SharedPreferences
import com.mnc.app.BuildConfig
import com.mnc.ext.gson.fromJsonOrNull
import com.mnc.ext.gson.toJson
import com.mnc.network.model.Auth

@Suppress("SameParameterValue")
class AppPreferences(val context: Context) {

    companion object {
        private const val SAVED_SESSION = "saved_auth"
    }

    // -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

    private val prefs: SharedPreferences by lazy {
        context.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE)
    }

    // -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

    var session: Auth?
        get() = findPreference(SAVED_SESSION, "").fromJsonOrNull()
        set(value) {
            putPreference(SAVED_SESSION, value?.toJson())
        }

    val accessToken: String get() = "Bearer ${session?.token.orEmpty()}"

    // -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

    fun storeSession(data: Auth?) {
        if (data == null) return else session = data
    }

    // -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

    @Suppress("UNCHECKED_CAST")
    private fun <T> findPreference(name: String, default: T?): T = with(prefs) {
        val res: Any? = when (default) {
            is Long -> getLong(name, default)
            is String -> getString(name, default)
            is Int -> getInt(name, default)
            is Boolean -> getBoolean(name, default)
            is Float -> getFloat(name, default)
            else -> throw IllegalArgumentException("Type is unknown")
        }
        res as T
    }

    private fun <T> putPreference(name: String, value: T) = with(prefs.edit()) {
        when (value) {
            is Long -> putLong(name, value)
            is String -> putString(name, value)
            is Int -> putInt(name, value)
            is Boolean -> putBoolean(name, value)
            is Float -> putFloat(name, value)
            else -> throw IllegalArgumentException("This type can't be saved into Preferences")
        }.apply()
    }

}