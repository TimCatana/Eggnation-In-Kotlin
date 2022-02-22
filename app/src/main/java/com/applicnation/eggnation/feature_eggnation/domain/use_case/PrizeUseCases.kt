package com.applicnation.eggnation.feature_eggnation.domain.use_case

import com.applicnation.eggnation.feature_eggnation.domain.use_case.prize_use_case.available_prize_use_case.AvailablePrizeGetAllUC
import com.applicnation.eggnation.feature_eggnation.domain.use_case.prize_use_case.available_prize_use_case.AvailablePrizeGetByRNGUC
import com.applicnation.eggnation.feature_eggnation.domain.use_case.prize_use_case.won_prize_use_case.WonPrizeAddToUserAccountUC
import com.applicnation.eggnation.feature_eggnation.domain.use_case.prize_use_case.won_prize_use_case.WonPrizeGetAllUC
import com.applicnation.eggnation.feature_eggnation.domain.use_case.prize_use_case.won_prize_use_case.WonPrizeGetByIdUC
import com.applicnation.eggnation.feature_eggnation.domain.use_case.prize_use_case.won_prize_use_case.WonPrizeUpdatePrizeClaimedUC

data class PrizeUseCases(
    val wonPrizeAddToUserAccountUC: WonPrizeAddToUserAccountUC,
    val wonPrizeUpdatePrizeClaimedUC: WonPrizeUpdatePrizeClaimedUC,
    val wonPrizeGetAllUC: WonPrizeGetAllUC,
    val wonPrizeGetByIdUC: WonPrizeGetByIdUC,

    val availablePrizeGetAllUC: AvailablePrizeGetAllUC,
    val availablePrizeGetByRNGUC: AvailablePrizeGetByRNGUC,
)