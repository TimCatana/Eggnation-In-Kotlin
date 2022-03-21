package com.applicnation.eggnation.feature_eggnation.data.repository

import com.applicnation.eggnation.feature_eggnation.data.local.PreferencesManager
import com.applicnation.eggnation.feature_eggnation.domain.repository.PreferencesRepository
import kotlinx.coroutines.flow.Flow

class PreferencesRepositoryImpl(
    private val preferencesManager: PreferencesManager
): PreferencesRepository {

    /**
     * DataStore Getters:
     * - TapCount
     * - LastResetTime
     */

    override fun getTapCount(): Flow<Int> {
        return preferencesManager.getTapCount()
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

    override suspend fun updateTapCount(tapCount: Int) {
        preferencesManager.updateTapCount(tapCount)
    }

    override suspend fun updateLastResetTime(resetTime: Long) {
        return preferencesManager.updateLastResetTime(resetTime)
    }

    override suspend fun decrementTapCounter(){
        return preferencesManager.decrementTapCounter()
    }
}