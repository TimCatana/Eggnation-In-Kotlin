package com.applicnation.eggnation.feature_eggnation.domain.use_case.database_use_case

import com.applicnation.eggnation.feature_eggnation.domain.modal.AvailablePrize
import com.applicnation.eggnation.feature_eggnation.domain.repository.DatabaseRepository
import javax.inject.Inject

class AvailablePrizeGetAllUC @Inject constructor(
    private val repository: DatabaseRepository
) {
    suspend operator fun invoke(): ArrayList<AvailablePrize> {
        return repository.getAllAvailablePrizes()
    }
}
