package com.applicnation.eggnation.feature_eggnation.domain.use_case.preference_use_case

import com.applicnation.eggnation.feature_eggnation.data.local.PreferencesManager
import com.applicnation.eggnation.feature_eggnation.data.local.UserPreferences
import com.applicnation.eggnation.feature_eggnation.domain.repository.AuthenticationRepository
import com.applicnation.eggnation.feature_eggnation.domain.repository.PreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PreferencesGet @Inject constructor(
    private val preferencesManager: PreferencesRepository
) {

    operator fun invoke(): Flow<UserPreferences> {
        return preferencesManager.getUserPreferences()
    }
}

