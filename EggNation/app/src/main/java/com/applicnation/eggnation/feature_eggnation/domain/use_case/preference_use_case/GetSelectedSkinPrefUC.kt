package com.applicnation.eggnation.feature_eggnation.domain.use_case.preference_use_case

import com.applicnation.eggnation.feature_eggnation.domain.repository.PreferencesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSelectedSkinPrefUC @Inject constructor(
    private val preferencesManager: PreferencesRepository
) {
    operator fun invoke(): Flow<Int> {
        return preferencesManager.getSelectedSkin()
    }
}
