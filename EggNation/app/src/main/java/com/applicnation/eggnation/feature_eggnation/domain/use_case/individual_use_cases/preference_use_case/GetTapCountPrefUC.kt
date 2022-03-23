package com.applicnation.eggnation.feature_eggnation.domain.use_case.individual_use_cases.preference_use_case

import com.applicnation.eggnation.feature_eggnation.domain.repository.PreferencesRepository
import kotlinx.coroutines.flow.*
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject

class GetTapCountPrefUC @Inject constructor(
    private val preferencesManager: PreferencesRepository
) {
    /**
     * TODO - add documentation
     */
    operator fun invoke(): Flow<Int> = flow {
        try {
            preferencesManager.getTapCount().collect { emit(it) }
        } catch (e: Exception) {
            Timber.e("Failed to get count from preferences --> $e")
        }
    }
}
