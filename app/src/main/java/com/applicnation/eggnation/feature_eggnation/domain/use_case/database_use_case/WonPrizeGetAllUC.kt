package com.applicnation.eggnation.feature_eggnation.domain.use_case.database_use_case

import com.applicnation.eggnation.feature_eggnation.domain.modal.WonPrize
import com.applicnation.eggnation.feature_eggnation.domain.repository.DatabaseRepository
import javax.inject.Inject

class WonPrizeGetAllUC @Inject constructor(
    private val repository: DatabaseRepository
) {
    suspend operator fun invoke(userId: String): ArrayList<WonPrize> {
        return repository.getAllWonPrizes(userId)
    }
}
