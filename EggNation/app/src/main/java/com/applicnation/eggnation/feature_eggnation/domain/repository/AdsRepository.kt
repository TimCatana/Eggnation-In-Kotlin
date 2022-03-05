package com.applicnation.eggnation.feature_eggnation.domain.repository

import android.app.Activity

interface AdsRepository {
    suspend fun loadAd(activityContext: Activity)
    suspend fun showAd(activityContext: Activity)
}
