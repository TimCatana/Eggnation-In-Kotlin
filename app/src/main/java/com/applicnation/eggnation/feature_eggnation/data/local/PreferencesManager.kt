package com.applicnation.eggnation.feature_eggnation.data.local

import android.content.Context
import android.util.Log
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.applicnation.eggnation.R
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import java.util.*
import java.util.prefs.Preferences
import javax.inject.Inject
import javax.inject.Singleton

// TODO - put the constants somewhere else (preferraby a constants file)
const val SELECTED_SKIN_DEFAULT: Int = R.drawable.egg
const val TAP_COUNT_DEFAULT: Int = 1000
const val LAST_RESET_TIME_DEFAULT: Long = 0
const val RECEIVES_NOTIFICATIONS_DEFAULT: Boolean = true

@Singleton
class PreferencesManager @Inject constructor(
    @ApplicationContext private val context: Context
) {

    // TODO - add profile picture preference

    private val Context.dataStore by preferencesDataStore(name = "preferences")

    private object PreferencesKeys {
        val SELECTED_SKIN = intPreferencesKey(name = "selectedSkin")
        val TAP_COUNT = intPreferencesKey(name = "tapCount")
        val LAST_RESET_TIME = longPreferencesKey(name = "lastResetTime")
        val RECEIVES_NOTIFICATIONS = booleanPreferencesKey(name = "receivesNotifications")
    }

    private val tapCountFlow: Flow<Int> = context.dataStore.data
        .catch { e ->
            if (e is IOException) {
                Log.e("PreferencesManager", "Failed to read tapcount from datastore")
                emit(emptyPreferences())
            } else {
                throw e
            }
        }
        .map { preferences ->
             preferences[PreferencesKeys.TAP_COUNT] ?: TAP_COUNT_DEFAULT
        }

    private val skinFlow: Flow<Int> = context.dataStore.data
        .catch { e ->
            if (e is IOException) {
                Log.e("PreferencesManager", "Failed to read skin from datastore")
                emit(emptyPreferences())
            } else {
                throw e
            }
        }
        .map { preferences ->
            preferences[PreferencesKeys.SELECTED_SKIN] ?: SELECTED_SKIN_DEFAULT
        }

    private val receivesNotificationsFlow: Flow<Boolean> = context.dataStore.data
        .catch { e ->
            if (e is IOException) {
                Log.e("PreferencesManager", "Failed to read receivesNotifications from datastore")
                emit(emptyPreferences())
            } else {
                throw e
            }
        }
        .map { preferences ->
            preferences[PreferencesKeys.RECEIVES_NOTIFICATIONS] ?: RECEIVES_NOTIFICATIONS_DEFAULT
        }

    private val lastResetTimeFlow: Flow<Long> = context.dataStore.data
        .catch { e ->
            if (e is IOException) {
                Log.e("PreferencesManager", "Failed to read lastResetTime from datastore")
                emit(emptyPreferences())
            } else {
                throw e
            }
        }
        .map { preferences ->
            preferences[PreferencesKeys.LAST_RESET_TIME] ?: LAST_RESET_TIME_DEFAULT
        }

    /**
     * DataStore Getters:
     * - SelectedSkin
     * - TapCount
     * - ReceivesNotifications
     * - LastResetTime
     */
    fun getSelectedSkin(): Flow<Int> {
        return skinFlow
    }

    fun getTapCount(): Flow<Int> {
        return tapCountFlow
    }

    fun getReceivesNotifications(): Flow<Boolean> {
        return receivesNotificationsFlow
    }

    fun getLastResetTime(): Flow<Long> {
        return lastResetTimeFlow
    }

    /**
     * DataStore Setters:
     * - SelectedSkin
     * - TapCount
     * - ReceivesNotifications
     * - LastResetTime
     */
    suspend fun updateSelectedSkin(skin: Int) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.SELECTED_SKIN] = skin
        }
    }

    suspend fun updateTapCount(tapCount: Int) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.TAP_COUNT] = tapCount
        }
    }

    suspend fun updateReceiveNotifications(receivesNotifications: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.RECEIVES_NOTIFICATIONS] = receivesNotifications
        }
    }

    suspend fun updateLastResetTime(resetTime: Long) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.LAST_RESET_TIME] = resetTime
        }
    }

    /**
     * DataStore Helpers
     */

    // TODO - need to figure out the coroutine scoping
    suspend fun decrementTapCounter() {
        tapCountFlow.map {
            updateTapCount(it - 1)
        }
    }

}

