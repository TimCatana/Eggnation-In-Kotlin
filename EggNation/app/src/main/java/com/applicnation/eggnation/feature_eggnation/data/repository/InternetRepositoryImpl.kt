package com.applicnation.eggnation.feature_eggnation.data.repository

import android.content.Context
import com.applicnation.eggnation.feature_eggnation.data.local.Internet
import com.applicnation.eggnation.feature_eggnation.data.remote.firebase.Functions
import com.applicnation.eggnation.feature_eggnation.domain.repository.InternetRepository
import javax.inject.Inject

class InternetRepositoryImpl @Inject constructor(
    private val internet: Internet
): InternetRepository  {

    override fun isOnline(context: Context): Boolean {
        return internet.isOnline(context)
    }
}