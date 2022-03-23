package com.applicnation.eggnation.feature_eggnation.domain.use_case

import com.applicnation.eggnation.feature_eggnation.domain.use_case.individual_use_cases.prize_use_case.available_prize_use_case.AvailablePrizeDeleteUC
import com.applicnation.eggnation.feature_eggnation.domain.use_case.individual_use_cases.prize_use_case.available_prize_use_case.AvailablePrizeGetAllUC
import com.applicnation.eggnation.feature_eggnation.domain.use_case.individual_use_cases.prize_use_case.available_prize_use_case.AvailablePrizeGetByRNGUC
import com.applicnation.eggnation.feature_eggnation.domain.use_case.individual_use_cases.prize_use_case.won_prize_use_case.*

data class PrizeUseCases(
    val wonPrizeAddToUserAccountUC: WonPrizeAddToUserAccountUC,
    val wonPrizesAddToAllWonPrizesUC: WonPrizeAddToAllWonPrizesUC,
    val wonPrizeUpdatePrizeClaimedUC: WonPrizeUpdatePrizeClaimedUC,
    val wonPrizeGetAllUC: WonPrizeGetAllUC,
    val wonPrizeGetByIdUC: WonPrizeGetByIdUC,

    val availablePrizeGetAllUC: AvailablePrizeGetAllUC,
    val availablePrizeGetByRNGUC: AvailablePrizeGetByRNGUC,
    val availablePrizeDeleteUC: AvailablePrizeDeleteUC
)