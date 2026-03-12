package com.pocketdev.data.local

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.pocketdev.domain.model.AppSettings
import com.pocketdev.domain.model.Theme

/**
 * Secure storage for app settings using EncryptedSharedPreferences
 */
class SettingsManager(context: Context) {

    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val sharedPreferences = EncryptedSharedPreferences.create(
        context,
        "pocketdev_settings",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun saveSettings(settings: AppSettings) {
        sharedPreferences.edit().apply {
            putString(KEY_GROQ_API_KEY, settings.groqApiKey)
            putString(KEY_THEME, settings.theme.name)
            putFloat(KEY_FONT_SIZE, settings.fontSize)
            putInt(KEY_TAB_SIZE, settings.tabSize)
            putBoolean(KEY_AUTO_SAVE, settings.autoSaveEnabled)
            putBoolean(KEY_AUTOCOMPLETE, settings.autocompleteEnabled)
            putBoolean(KEY_LINE_NUMBERS, settings.lineNumbersEnabled)
            putInt(KEY_AUTO_SAVE_INTERVAL, settings.autoSaveIntervalSeconds)
        }.apply()
    }

    fun getSettings(): AppSettings {
        return AppSettings(
            groqApiKey = sharedPreferences.getString(KEY_GROQ_API_KEY, "") ?: "",
            theme = Theme.valueOf(sharedPreferences.getString(KEY_THEME, Theme.DARK.name) ?: Theme.DARK.name),
            fontSize = sharedPreferences.getFloat(KEY_FONT_SIZE, 14f),
            tabSize = sharedPreferences.getInt(KEY_TAB_SIZE, 4),
            autoSaveEnabled = sharedPreferences.getBoolean(KEY_AUTO_SAVE, true),
            autocompleteEnabled = sharedPreferences.getBoolean(KEY_AUTOCOMPLETE, true),
            lineNumbersEnabled = sharedPreferences.getBoolean(KEY_LINE_NUMBERS, true),
            autoSaveIntervalSeconds = sharedPreferences.getInt(KEY_AUTO_SAVE_INTERVAL, 30)
        )
    }

    fun saveGroqApiKey(apiKey: String) {
        sharedPreferences.edit().putString(KEY_GROQ_API_KEY, apiKey).apply()
    }

    fun getGroqApiKey(): String {
        return sharedPreferences.getString(KEY_GROQ_API_KEY, "") ?: ""
    }

    fun isGroqApiKeySet(): Boolean {
        return !getGroqApiKey().isNullOrBlank()
    }

    companion object {
        private const val KEY_GROQ_API_KEY = "groq_api_key"
        private const val KEY_THEME = "theme"
        private const val KEY_FONT_SIZE = "font_size"
        private const val KEY_TAB_SIZE = "tab_size"
        private const val KEY_AUTO_SAVE = "auto_save"
        private const val KEY_AUTOCOMPLETE = "autocomplete"
        private const val KEY_LINE_NUMBERS = "line_numbers"
        private const val KEY_AUTO_SAVE_INTERVAL = "auto_save_interval"
    }
}
