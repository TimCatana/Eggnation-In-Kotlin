package com.applicnation.eggnation.feature_eggnation.data.repository

import com.applicnation.eggnation.feature_eggnation.data.remote.firebase.AdMob
import com.applicnation.eggnation.feature_eggnation.domain.repository.AdsRepository
import javax.inject.Inject

class AdsRepositoryImpl @Inject constructor(
    private val adMob: AdMob
): AdsRepository {
    override suspend fun loadAd() {
        adMob.loadInterstitialAd()
    }

    override suspend fun showAd() {
        adMob.playInterstitialAd()
    }
}
