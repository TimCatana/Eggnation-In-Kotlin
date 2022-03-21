package com.applicnation.eggnation.feature_eggnation.domain.repository

import android.content.Context

interface InternetRepository {
    fun isOnline(context: Context): Boolean
}