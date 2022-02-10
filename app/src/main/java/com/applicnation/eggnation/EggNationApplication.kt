package com.applicnation.eggnation

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class EggNationApplication: Application() {

// Add this if you want to add timber later on
//    override fun onCreate() {
//        super.onCreate()
//        if (BuildConfig.DEBUG) {
//            Timber.plant(Timber.DebugTree())
//        }
//    }
}