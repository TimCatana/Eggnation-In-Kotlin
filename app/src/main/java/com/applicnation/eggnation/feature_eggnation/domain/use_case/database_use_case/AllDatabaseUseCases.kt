package com.applicnation.eggnation.feature_eggnation.domain.use_case.database_use_case

data class AllDatabaseUseCases(
    /**
     * Firestore
     */
    val userRegisterUC: UserRegisterUC,
    val userUpdateEmailUC: UserUpdateEmailUC,
    val userUpdateUsernameUC: UserUpdateUsernameUC,

    val wonPrizeAddToUserAccountUC: WonPrizeAddToUserAccountUC,
    val wonPrizeGetAllUC: WonPrizeGetAllUC,
    val wonPrizeGetByIdUC: WonPrizeGetByIdUC,
    val wonPrizeUpdatePrizeClaimedUC: WonPrizeUpdatePrizeClaimedUC,

    /**
     * Realtime Database
     */
    val incrementGlobalCounterUC: IncrementGlobalCounterUC,

    val availablePrizeGetAllUC: AvailablePrizeGetAllUC,
    val availablePrizeGetByRNGUC: AvailablePrizeGetByRNGUC
)
