package com.applicnation.eggnation.feature_eggnation.domain.use_case.user_use_case.game_logic_use_case

import com.applicnation.eggnation.feature_eggnation.domain.repository.AuthenticationRepository
import com.applicnation.eggnation.feature_eggnation.domain.repository.DatabaseRepository
import javax.inject.Inject

class IncrementGlobalCounterUC @Inject constructor(
    private val repository: DatabaseRepository
) {

    suspend operator fun invoke() {
        // TODO - add try catch for errors. This function can silently die
        repository.incrementGlobalCounter()
    }
}