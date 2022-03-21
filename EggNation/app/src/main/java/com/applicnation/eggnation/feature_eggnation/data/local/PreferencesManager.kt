package com.applicnation.eggnation.feature_eggnation.data.local

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.applicnation.eggnation.util.Constants
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferencesManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val Context.dataStore by preferencesDataStore(name = Constants.PREFS_NAME)

    private object PreferencesKeys {
        val TAP_COUNT = intPreferencesKey("tapCount")
        val LAST_RESET_TIME = longPreferencesKey("lastResetTime")
    }

    /**
     * Preferences:
     * - tapCount (Int) --> Should always be between 0-1000
     * - skin (Int) --> The selected skin
     * - receivesNotifications (Boolean)
     * - lastResetTime (Long) --> Should always represent the last time the counter was reset in milliseconds
     * TODO - add preference to tell whether user is logged in or not
     *
     */

    /**
     * Get's the tapCount from preference datastore and stores it in a variable
     * @exception IOException Failed to read from disk, emit empty
     * @exception Exception All exceptions thrown from this catch block are UNEXPECTED
     */
    private val tapCountFlow: Flow<Int> = context.dataStore.data
        .catch { e ->
            if (e is IOException) {
                Timber.e("Failed to read tapCount from preference datastore: IOException  --> $e")
                emit(emptyPreferences())
            } else {
                Timber.wtf("Failed to read receivesNotifications from preference datastore: Unexpected Exception  --> $e")
                throw e
            }
        }
        .map { preferences ->
            preferences[PreferencesKeys.TAP_COUNT] ?: Constants.PREFS_TAP_COUNT_DEFAULT
        }

    /**
     * Get's the last reset time (date in milliseconds) from preference datastore and stores it in a variable
     * @exception IOException Failed to read from disk, emit empty
     * @exception Exception All exceptions thrown from this catch block are UNEXPECTED
     */
    private val lastResetTimeFlow: Flow<Long> = context.dataStore.data
        .catch { e ->
            if (e is IOException) {
                Timber.e("Failed to read lastResetTime from preference datastore: IOException --> $e")
                emit(emptyPreferences())
            } else {
                Timber.wtf("Failed to read lastResetTime from preference datastore: Unexpected Exception --> $e")
                throw e
            }
        }
        .map { preferences ->
            preferences[PreferencesKeys.LAST_RESET_TIME] ?: Constants.PREFS_LAST_RESET_TIME_DEFAULT
        }

    /**
     * DataStore Getters:
     * - TapCount
     * - LastResetTime
     * // TODO - What happens when IOException occurs?
     */

    fun getTapCount(): Flow<Int> {
        return tapCountFlow
    }

    fun getLastResetTime(): Flow<Long> {
        return lastResetTimeFlow
    }

    /**
     * DataStore Setters:
     * - TapCount
     * - LastResetTime
     */

    /**
     * Updates the tapCount in preference datastore
     * @param tapCount The new tapCount (should be either 1000, or currentTapCount - 1)
     * @exception IOException Failed to write to disk
     * @exception Exception All exceptions thrown from this catch block are UNEXPECTED
     */
    suspend fun updateTapCount(tapCount: Int) {
        try {
            context.dataStore.edit { preferences ->
                preferences[PreferencesKeys.TAP_COUNT] = tapCount
            }
        } catch (e: IOException) {
            Timber.e("Failed to update ${PreferencesKeys.TAP_COUNT} key in preference datastore: IOException --> $e")
        } catch (e: Exception) {
            Timber.wtf("Failed to update ${PreferencesKeys.TAP_COUNT} key in preference datastore: Unknown Exception--> $e")
        }
    }

    /**
     * Updates the last reset time in preference datastore
     * @param resetTime The latest time that the tap countre was reset (should represent a date in milliseconds)
     * @exception IOException Failed to write to disk
     * @exception Exception All exceptions thrown from this catch block are UNEXPECTED
     */
    suspend fun updateLastResetTime(resetTime: Long) {
        try {
            context.dataStore.edit { preferences ->
                preferences[PreferencesKeys.LAST_RESET_TIME] = resetTime
            }
        } catch (e: IOException) {
            Timber.e("Failed to update ${PreferencesKeys.LAST_RESET_TIME} key in preference datastore: IOException --> $e")
        } catch (e: Exception) {
            Timber.wtf("Failed to update ${PreferencesKeys.LAST_RESET_TIME} key in preference datastore: Unknown Exception--> $e")
        }
    }

    /**
     * DataStore Helpers
     */
    suspend fun decrementTapCounter() {
        tapCountFlow.map {
            if (it in 1..999) {
                updateTapCount(it - 1)
            }
        }
    }
}

