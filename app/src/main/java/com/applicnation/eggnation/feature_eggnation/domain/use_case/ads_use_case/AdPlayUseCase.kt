package com.applicnation.eggnation.feature_eggnation.domain.use_case.ads_use_case

import android.app.Activity
import android.content.Context
import com.applicnation.eggnation.feature_eggnation.data.remote.firebase.AdMob
import timber.log.Timber
import javax.inject.Inject

class AdPlayUseCase @Inject constructor(
    private val adMob: AdMob
) {
    operator fun invoke(activityContext: Activity) {
        try {
            adMob.playInterstitialAd(activityContext)
        } catch( e: Exception) {
            Timber.e("Failed to play add")
        }
    }
}