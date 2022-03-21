package com.applicnation.eggnation.feature_eggnation.domain.use_case.preference_use_case

import com.applicnation.eggnation.feature_eggnation.domain.repository.PreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject

class DecrementTapCountPrefUC @Inject constructor(
    private val preferencesManager: PreferencesRepository

) {
    operator fun invoke(): Flow<Int> = flow {
        try {
            preferencesManager.decrementTapCounter()
        } catch (e: Exception) {
            Timber.e("Failed to update local counter")
            return@flow
        }

        preferencesManager.getTapCount().collectLatest {
            emit(it)
        }
    }
}
