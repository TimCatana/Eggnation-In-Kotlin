package com.applicnation.eggnation.feature_eggnation.domain.use_case.database_use_case

import com.applicnation.eggnation.feature_eggnation.domain.repository.DatabaseRepository
import javax.inject.Inject

class IncrementGlobalCounterUC @Inject constructor(
    private val database: DatabaseRepository
) {
    suspend operator fun invoke() {
        database.incrementGlobalCounter()
    }
}
