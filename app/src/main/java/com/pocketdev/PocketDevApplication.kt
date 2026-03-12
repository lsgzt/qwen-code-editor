package com.pocketdev

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.pocketdev.data.local.AppDatabase
import com.pocketdev.data.local.SettingsManager

/**
 * Main Application class for PocketDev
 */
class PocketDevApplication : Application() {

    val database: AppDatabase by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        ).build()
    }

    val settingsManager: SettingsManager by lazy {
        SettingsManager(applicationContext)
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: PocketDevApplication
            private set

        val database: AppDatabase
            get() = instance.database

        val settingsManager: SettingsManager
            get() = instance.settingsManager
    }
}
