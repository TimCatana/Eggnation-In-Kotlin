package com.applicnation.eggnation.feature_eggnation.domain.use_case

import com.applicnation.eggnation.feature_eggnation.domain.use_case.ads_use_case.AdLoadUseCase
import com.applicnation.eggnation.feature_eggnation.domain.use_case.ads_use_case.AdPlayUseCase

data class AdUseCases(
    val adLoadUseCase: AdLoadUseCase,
    val adPlayUseCase: AdPlayUseCase
)
