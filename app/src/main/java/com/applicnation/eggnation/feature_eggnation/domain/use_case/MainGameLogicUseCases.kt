package com.applicnation.eggnation.feature_eggnation.domain.use_case

import DoGameLogicUC
import com.applicnation.eggnation.feature_eggnation.data.remote.firebase.AdMob
import com.applicnation.eggnation.feature_eggnation.domain.repository.DatabaseRepository
import com.applicnation.eggnation.feature_eggnation.domain.use_case.ads_use_case.AdLoadUseCase
import com.applicnation.eggnation.feature_eggnation.domain.use_case.ads_use_case.AdPlayUseCase
import com.applicnation.eggnation.feature_eggnation.domain.use_case.user_use_case.game_logic_use_case.ClaimPrizeUC
import com.applicnation.eggnation.feature_eggnation.domain.use_case.user_use_case.game_logic_use_case.IncrementGlobalCounterUC
import com.applicnation.eggnation.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

data class MainGameLogicUseCases(
    val claimPrizeUC: ClaimPrizeUC,
    val incrementGlobalCounterUC: IncrementGlobalCounterUC,
    val doGameLogicUC: DoGameLogicUC
)