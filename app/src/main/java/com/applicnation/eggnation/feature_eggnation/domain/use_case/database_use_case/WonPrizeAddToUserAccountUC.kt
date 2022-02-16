package com.applicnation.eggnation.feature_eggnation.domain.use_case.database_use_case

import com.applicnation.eggnation.feature_eggnation.domain.repository.DatabaseRepository
import javax.inject.Inject

class WonPrizeAddToUserAccountUC @Inject constructor(
    private val repository: DatabaseRepository
) {
    suspend operator fun invoke(
        userId: String,
        prizeId: String,
        prizeTitle: String,
        prizeDesc: String,
        prizeTier: String
    ) {
        repository.addWonPrizeToUserAccount(userId, prizeId, prizeTitle, prizeDesc, prizeTier)
    }
}
