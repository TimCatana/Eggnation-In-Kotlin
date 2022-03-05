package com.applicnation.eggnation

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.FormatStrategy
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class EggNationApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        val formatStrategy: FormatStrategy =
            PrettyFormatStrategy
                .newBuilder()
                .showThreadInfo(true)
                .methodCount(1)
                .methodOffset(5)
                .tag("")
                .build()

        Logger.addLogAdapter(AndroidLogAdapter(formatStrategy))

        if (BuildConfig.DEBUG) {
            Timber.plant(object : Timber.DebugTree() {
                override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                    Logger.log(priority, "--$tag", message, t)
                }
            })
        }

        Timber.i("Tree Planted")
    }
}