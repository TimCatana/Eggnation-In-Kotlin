package com.applicnation.eggnation.feature_eggnation.domain.repository

interface AdsRepository {
    suspend fun loadAd()
    suspend fun showAd()
}
