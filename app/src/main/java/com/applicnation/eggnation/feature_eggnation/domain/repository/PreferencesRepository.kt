package com.applicnation.eggnation.feature_eggnation.domain.repository

import com.applicnation.eggnation.feature_eggnation.data.local.UserPreferences
import kotlinx.coroutines.flow.Flow

interface PreferencesRepository {
    fun getUserPreferences(): Flow<UserPreferences>
    suspend fun updateSelectedSkin(skin: Int)
    suspend fun updateReceiveNotifications(receivesNotifications: Boolean)
}