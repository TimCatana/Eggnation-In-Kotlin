package com.applicnation.eggnation.feature_eggnation.domain.use_case.preference_use_case

import com.applicnation.eggnation.feature_eggnation.domain.repository.PreferencesRepository
import javax.inject.Inject

class UpdateReceivesNotificationsPrefUC @Inject constructor(
    private val preferencesManager: PreferencesRepository

) {
    suspend operator fun invoke(receivesNotifications: Boolean){
        preferencesManager.updateReceiveNotifications(receivesNotifications)
    }
}

