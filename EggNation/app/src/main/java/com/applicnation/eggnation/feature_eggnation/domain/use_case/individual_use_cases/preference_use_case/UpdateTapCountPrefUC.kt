package com.applicnation.eggnation.feature_eggnation.domain.use_case.individual_use_cases.preference_use_case

import com.applicnation.eggnation.feature_eggnation.domain.repository.PreferencesRepository
import javax.inject.Inject

class UpdateTapCountPrefUC @Inject constructor(
    private val preferencesManager: PreferencesRepository

) {
    suspend operator fun invoke(tapCount: Int){
        preferencesManager.updateTapCount(tapCount)
    }
}