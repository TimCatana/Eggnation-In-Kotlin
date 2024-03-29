package com.applicnation.eggnation.feature_eggnation.data.local

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.applicnation.eggnation.util.Constants
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
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
        val SELECTED_SKIN = intPreferencesKey("selectedSkin")
        val TAP_COUNT = intPreferencesKey("tapCount")
        val LAST_RESET_TIME = longPreferencesKey("lastResetTime")
        val RECEIVES_NOTIFICATIONS = booleanPreferencesKey("receivesNotifications")
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
     * Get's the selected skin from preference datastore and stores it in a variable
     * @exception IOException Failed to read from disk, emit empty
     * @exception Exception All exceptions thrown from this catch block are UNEXPECTED
     */
    private val skinFlow: Flow<Int> = context.dataStore.data
        .catch { e ->
            if (e is IOException) {
                Timber.e("Failed to read skin from preference datastore: IOException  --> $e")
                emit(emptyPreferences())
            } else {
                Timber.wtf("Failed to read receivesNotifications from preference datastore: Unexpected Exception  --> $e")
                throw e
            }
        }
        .map { preferences ->
            preferences[PreferencesKeys.SELECTED_SKIN] ?: Constants.PREFS_SELECTED_SKIN_DEFAULT
        }

    /**
     * Get's the receives notifications status from preference datastore and stores it in a variable
     * @exception IOException Failed to read from disk, emit empty
     * @exception Exception All exceptions thrown from this catch block are UNEXPECTED
     */
    private val receivesNotificationsFlow: Flow<Boolean> = context.dataStore.data
        .catch { e ->
            if (e is IOException) {
                Timber.e("Failed to read receivesNotifications from preference datastore: IOException  --> $e")
                emit(emptyPreferences())
            } else {
                Timber.wtf("Failed to read receivesNotifications from preference datastore: Unexpected Exception  --> $e")
                throw e
            }
        }
        .map { preferences ->
            preferences[PreferencesKeys.RECEIVES_NOTIFICATIONS]
                ?: Constants.PREFS_RECEIVES_NOTIFICATIONS_DEFAULT
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
     * - SelectedSkin
     * - TapCount
     * - ReceivesNotifications
     * - LastResetTime
     * // TODO - What happens when IOException occurs?
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

    /**
     * Updates the selected skin in preference datastore
     * @param skin The skin that was selected (should be a R.drawable resource file, which are mapped to Ints)
     * @exception IOException Failed to write to disk
     * @exception Exception All exceptions thrown from this catch block are UNEXPECTED
     */
    suspend fun updateSelectedSkin(skin: Int) {
        try {
            context.dataStore.edit { preferences ->
                preferences[PreferencesKeys.SELECTED_SKIN] = skin
            }
        } catch (e: IOException) {
            Timber.e("Failed to update ${PreferencesKeys.SELECTED_SKIN} key in preference datastore: IOException --> $e")
        } catch (e: Exception) {
            Timber.wtf("Failed to update ${PreferencesKeys.SELECTED_SKIN} key in preference datastore: Unknown Exception--> $e")
        }

    }

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
     * Updates the receivesNotifications status in preference datastore
     * @param receivesNotifications The new receivesNotifications status
     * @exception IOException Failed to write to disk
     * @exception Exception All exceptions thrown from this catch block are UNEXPECTED
     */
    suspend fun updateReceiveNotifications(receivesNotifications: Boolean) {
        try {
            context.dataStore.edit { preferences ->
                preferences[PreferencesKeys.RECEIVES_NOTIFICATIONS] = receivesNotifications
            }
        } catch (e: IOException) {
            Timber.e("Failed to update ${PreferencesKeys.RECEIVES_NOTIFICATIONS} key in preference datastore: IOException --> $e")
        } catch (e: Exception) {
            Timber.wtf("Failed to update ${PreferencesKeys.RECEIVES_NOTIFICATIONS} key in preference datastore: Unknown Exception--> $e")
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
    // TODO - need to figure out the coroutine scoping
    suspend fun decrementTapCounter() {
        tapCountFlow.map {
            updateTapCount(it - 1)
        }
    }

}

