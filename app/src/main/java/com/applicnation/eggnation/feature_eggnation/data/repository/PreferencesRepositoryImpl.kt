package com.applicnation.eggnation.feature_eggnation.data.repository

import com.applicnation.eggnation.feature_eggnation.data.local.PreferencesManager
import com.applicnation.eggnation.feature_eggnation.data.local.UserPreferences
import com.applicnation.eggnation.feature_eggnation.domain.repository.PreferencesRepository
import kotlinx.coroutines.flow.Flow

class PreferencesRepositoryImpl(
    private val preferencesManager: PreferencesManager
): PreferencesRepository {

    override fun getUserPreferences(): Flow<UserPreferences> {
        return preferencesManager.getUserPreferences()
    }

    override suspend fun updateSelectedSkin(skin: Int) {
        preferencesManager.updateSelectedSkin(skin)
    }

    override suspend fun updateReceiveNotifications(receivesNotifications: Boolean) {
        preferencesManager.updateReceiveNotifications(receivesNotifications)
    }


}