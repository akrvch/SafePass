package com.example.safepass.storage

import android.content.Context
import android.content.SharedPreferences

object PinManager {
    private const val PREFS_NAME = "safepass_prefs"
    private const val KEY_PIN = "pin_code"
    private lateinit var prefs: SharedPreferences

    fun init(context: Context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun isPinSet(): Boolean = prefs.contains(KEY_PIN)

    fun savePIN(pin: String) {
        prefs.edit().putString(KEY_PIN, pin).apply()
    }

    fun verifyPIN(pin: String): Boolean =
        prefs.getString(KEY_PIN, null) == pin
}