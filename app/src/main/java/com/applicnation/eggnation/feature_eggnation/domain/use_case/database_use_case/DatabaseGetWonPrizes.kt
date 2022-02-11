package com.applicnation.eggnation.feature_eggnation.domain.use_case.database_use_case

import com.applicnation.eggnation.feature_eggnation.domain.repository.DatabaseRepository

class DatabaseGetWonPrizes(
    private val repository: DatabaseRepository
) {

    suspend operator fun invoke() {
        // TODO - make a getWonPrizes() function
    }
}