package com.applicnation.eggnation.feature_eggnation.data.repository

import com.applicnation.eggnation.feature_eggnation.data.local.PreferencesManager
import com.applicnation.eggnation.feature_eggnation.domain.repository.PreferencesRepository
import kotlinx.coroutines.flow.Flow

class PreferencesRepositoryImpl(
    private val preferencesManager: PreferencesManager
): PreferencesRepository {

    /**
     * DataStore Getters:
     * - SelectedSkin
     * - TapCount
     * - ReceivesNotifications
     * - LastResetTime
     */
    override fun getSelectedSkin(): Flow<Int> {
        return preferencesManager.getSelectedSkin()
    }

    override fun getTapCount(): Flow<Int> {
        return preferencesManager.getTapCount()
    }

    override fun getReceivesNotifications(): Flow<Boolean> {
        return preferencesManager.getReceivesNotifications()
    }

    override fun getLastResetTime(): Flow<Long> {
        return preferencesManager.getLastResetTime()
    }

    /**
     * DataStore Setters:
     * - SelectedSkin
     * - TapCount
     * - ReceivesNotifications
     * - LastResetTime
     */
    override suspend fun updateSelectedSkin(skin: Int) {
        preferencesManager.updateSelectedSkin(skin)
    }

    override suspend fun updateTapCount(tapCount: Int) {
        preferencesManager.updateTapCount(tapCount)
    }

    override suspend fun updateReceiveNotifications(receivesNotifications: Boolean) {
        preferencesManager.updateReceiveNotifications(receivesNotifications)
    }

    override suspend fun updateLastResetTime(resetTime: Long) {
        return preferencesManager.updateLastResetTime(resetTime)
    }

    override suspend fun decrementTapCounter() {
        return preferencesManager.decrementTapCounter()
    }
}