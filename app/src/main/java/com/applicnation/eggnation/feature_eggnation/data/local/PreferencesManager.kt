package com.applicnation.eggnation.feature_eggnation.data.local

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.applicnation.eggnation.R
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import java.util.prefs.Preferences
import javax.inject.Inject
import javax.inject.Singleton

data class UserPreferences(
    val selectedSkin: Int,
    val receivesNotifications: Boolean
)

@Singleton
class PreferencesManager @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val Context.dataStore by preferencesDataStore(name = "preferences")

    private val userPreferences: Flow<UserPreferences> = context.dataStore.data
        .catch { e ->
            if (e is IOException) {
                Log.e("PreferencesManager", "Failed to read from datastore")
                emit(emptyPreferences())
            } else {
                throw e
            }
        }
        .map { preferences ->
            val selectedSkin = preferences[PreferencesKeys.SELECTED_SKIN]
                ?: SELECTED_SKIN_DEFAULT
            val receivesNotifications = preferences[PreferencesKeys.RECEIVES_NOTIFICATIONS]
                ?: RECEIVES_NOTIFICATIONS_DEFAULT
            UserPreferences(
                selectedSkin = selectedSkin,
                receivesNotifications = receivesNotifications
            )
        }

    fun getUserPreferences(): Flow<UserPreferences>{
        return userPreferences
    }

    suspend fun updateSelectedSkin(skin: Int) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.SELECTED_SKIN] = skin
        }
    }

    suspend fun updateReceiveNotifications(receivesNotifications: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.RECEIVES_NOTIFICATIONS] = receivesNotifications
        }
    }

    private object PreferencesKeys {
        val SELECTED_SKIN = intPreferencesKey(name = "selectedSkin")
        val RECEIVES_NOTIFICATIONS = booleanPreferencesKey(name = "receivesNotifications")
    }
}

const val SELECTED_SKIN_DEFAULT = R.drawable.egg
const val RECEIVES_NOTIFICATIONS_DEFAULT = true