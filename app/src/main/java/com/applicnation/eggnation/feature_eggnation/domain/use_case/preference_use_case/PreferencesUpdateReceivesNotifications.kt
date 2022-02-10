package com.applicnation.eggnation.feature_eggnation.domain.use_case.preference_use_case

import com.applicnation.eggnation.feature_eggnation.data.local.PreferencesManager
import com.applicnation.eggnation.feature_eggnation.domain.repository.PreferencesRepository
import javax.inject.Inject

class PreferencesUpdateReceivesNotifications @Inject constructor(
    private val preferencesManager: PreferencesRepository

) {

    suspend operator fun invoke(skin: Int){
        preferencesManager.updateSelectedSkin(skin)
    }
}
