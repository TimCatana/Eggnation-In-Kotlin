package com.applicnation.eggnation.feature_eggnation.domain.use_case.database_use_case

import com.applicnation.eggnation.feature_eggnation.domain.modal.AvailablePrize
import com.applicnation.eggnation.feature_eggnation.domain.repository.DatabaseRepository
import javax.inject.Inject

class AvailablePrizeGetByRNGUC @Inject constructor(
    private val repository: DatabaseRepository
) {
    suspend operator fun invoke(rng: String): AvailablePrize {
        return repository.getAvailablePrizeByRNG(rng)
    }
}
