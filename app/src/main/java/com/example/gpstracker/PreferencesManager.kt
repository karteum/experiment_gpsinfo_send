package com.example.gpstracker

import android.content.Context
import android.content.SharedPreferences

class PreferencesManager private constructor(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREFS_NAME = "gps_tracker_prefs"
        private const val KEY_MMSI = "mmsi"
        private const val KEY_PHONE = "phone_number"
        private const val KEY_EMAIL = "target_email"

        @Volatile
        private var instance: PreferencesManager? = null

        fun getInstance(context: Context): PreferencesManager {
            return instance ?: synchronized(this) {
                instance ?: PreferencesManager(context.applicationContext).also { instance = it }
            }
        }
    }

    // Save methods
    fun saveMMSI(mmsi: String) {
        prefs.edit().putString(KEY_MMSI, mmsi).apply()
    }

    fun savePhoneNumber(phone: String) {
        prefs.edit().putString(KEY_PHONE, phone).apply()
    }

    fun saveTargetEmail(email: String) {
        prefs.edit().putString(KEY_EMAIL, email).apply()
    }

    // Get methods
    fun getMMSI(): String {
        return prefs.getString(KEY_MMSI, "") ?: ""
    }

    fun getPhoneNumber(): String {
        return prefs.getString(KEY_PHONE, "") ?: ""
    }

    fun getTargetEmail(): String {
        return prefs.getString(KEY_EMAIL, "") ?: ""
    }

    // Check if all settings are configured
    fun areSettingsConfigured(): Boolean {
        return getMMSI().isNotEmpty() &&
               getPhoneNumber().isNotEmpty() &&
               getTargetEmail().isNotEmpty()
    }
}
