package com.applicnation.eggnation.feature_eggnation.domain.use_case.database_use_case

import com.applicnation.eggnation.feature_eggnation.domain.modal.WonPrize
import com.applicnation.eggnation.feature_eggnation.domain.repository.DatabaseRepository

class DatabaseGetWonPrizeById(
    private val repository: DatabaseRepository
) {

    suspend operator fun invoke(userId: String, prizeId: String): WonPrize  {
        // TODO - make a getWonPrizes() function
        return repository.getWonPrizeById(userId, prizeId)
    }
}