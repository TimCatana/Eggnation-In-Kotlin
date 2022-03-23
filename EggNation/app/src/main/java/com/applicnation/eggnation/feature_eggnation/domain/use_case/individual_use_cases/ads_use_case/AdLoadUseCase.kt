package com.applicnation.eggnation.feature_eggnation.domain.use_case.individual_use_cases.ads_use_case

import android.app.Activity
import com.applicnation.eggnation.feature_eggnation.data.remote.firebase.AdMob
import timber.log.Timber
import javax.inject.Inject

class AdLoadUseCase @Inject constructor(
    private val adMob: AdMob
) {

    /**
     * TODO - add documentation
     */
    operator fun invoke(activityContext: Activity) {
        try {
            adMob.loadInterstitialAd(activityContext)
        } catch( e: Exception) {
            Timber.e("Failed to load ad")
        }
    }
}
