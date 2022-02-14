package com.applicnation.eggnation.feature_eggnation.domain.use_case.ads_use_case

import com.applicnation.eggnation.feature_eggnation.data.remote.firebase.AdMob
import com.applicnation.eggnation.feature_eggnation.domain.repository.AuthenticationRepository
import javax.inject.Inject

class AdLoadUseCase @Inject constructor(
    private val adMob: AdMob
) {

    suspend operator fun invoke() {
        adMob.loadInterstitialAd()
    }

}
