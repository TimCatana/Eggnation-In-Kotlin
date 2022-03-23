package com.applicnation.eggnation.feature_eggnation.domain.use_case.screen_use_cases.game

import DoGameLogicUC
import com.applicnation.eggnation.feature_eggnation.domain.use_case.individual_use_cases.ads_use_case.AdLoadUseCase
import com.applicnation.eggnation.feature_eggnation.domain.use_case.individual_use_cases.ads_use_case.AdPlayUseCase
import com.applicnation.eggnation.feature_eggnation.domain.use_case.individual_use_cases.preference_use_case.*
import com.applicnation.eggnation.feature_eggnation.domain.use_case.individual_use_cases.user_use_case.game_logic_use_case.IncrementGlobalCounterUC
import com.applicnation.eggnation.feature_eggnation.domain.use_case.individual_use_cases.user_use_case.game_logic_use_case.InternetConnectivityUC

data class HomeScreenUseCases(
    val getTapCountPrefUC: GetTapCountPrefUC,
    val incrementGlobalCounterUC: IncrementGlobalCounterUC,
    val internetConnectivityUC: InternetConnectivityUC,
    val decrementTapCountPrefUC: DecrementTapCountPrefUC,

    val getLastResetTimePrefUC: GetLastResetTimePrefUC,
    val updateLastResetTimePrefUC: UpdateLastResetTimePrefUC,
    val updateTapCountPrefUC: UpdateTapCountPrefUC,

    val adPlayUseCase: AdPlayUseCase,
    val adLoadUseCase: AdLoadUseCase,

    val doGameLogicUC: DoGameLogicUC
)