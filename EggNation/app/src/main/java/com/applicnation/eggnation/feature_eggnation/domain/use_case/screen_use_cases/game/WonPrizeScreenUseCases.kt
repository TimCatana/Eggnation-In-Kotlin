package com.applicnation.eggnation.feature_eggnation.domain.use_case.screen_use_cases.game

import com.applicnation.eggnation.feature_eggnation.domain.use_case.individual_use_cases.prize_use_case.won_prize_use_case.WonPrizeGetAllUC
import com.applicnation.eggnation.feature_eggnation.domain.use_case.individual_use_cases.user_use_case.get_user_data_use_case.GetUserEmailVerificationStatusUC

data class WonPrizeScreenUseCases(
    val wonPrizeGetAllUC: WonPrizeGetAllUC,
    val getUserEmailVerificationStatusUC: GetUserEmailVerificationStatusUC
)