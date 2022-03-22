package com.applicnation.eggnation.feature_eggnation.domain.use_case.user_use_case.game_logic_use_case

import com.applicnation.eggnation.feature_eggnation.domain.repository.DatabaseRepository
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject

class IncrementGlobalCounterUC @Inject constructor(
    private val repository: DatabaseRepository
) {

    /**
     * TODO - add documentation
     */
    suspend operator fun invoke() {
        try {
            repository.incrementGlobalCounter()
        } catch (e: Exception) {
            Timber.e("REALTIME DATABASE: Failed to update global counter")
        }
    }
}