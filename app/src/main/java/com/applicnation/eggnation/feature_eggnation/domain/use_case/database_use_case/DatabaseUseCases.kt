package com.applicnation.eggnation.feature_eggnation.domain.use_case.database_use_case

import com.applicnation.eggnation.feature_eggnation.domain.use_case.preference_use_case.*

data class DatabaseUseCases(
    val databaseGetAvailablePrizeByRNG: DatabaseGetAvailablePrizeByRNG,
    val databaseGetAvailablePrizes: DatabaseGetAvailablePrizes,
    val databaseGetWonPrizes: DatabaseGetWonPrizes,
    val databaseGetWonPrizeById: DatabaseGetWonPrizeById,
    val databaseIncrementGlobalCounter: DatabaseIncrementGlobalCounter,
    val databaseRegisterUser: DatabaseRegisterUser
)