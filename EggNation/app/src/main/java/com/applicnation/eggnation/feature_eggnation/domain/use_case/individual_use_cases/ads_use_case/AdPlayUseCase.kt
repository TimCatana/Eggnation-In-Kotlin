package com.applicnation.eggnation.feature_eggnation.domain.use_case.individual_use_cases.ads_use_case

import android.app.Activity
import com.applicnation.eggnation.feature_eggnation.data.remote.firebase.AdMob
import timber.log.Timber
import javax.inject.Inject

class AdPlayUseCase @Inject constructor(
    private val adMob: AdMob
) {

    /**
     * TODO - add documentation
     */
    operator fun invoke(activityContext: Activity): Boolean {
        try {
            adMob.playInterstitialAd(activityContext)
            Timber.i("Successfully played ad")
            return true
        } catch( e: Exception) {
            Timber.e("Failed to play ad -> $e")
            return false
        }
    }
}
