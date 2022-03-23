package com.applicnation.eggnation.feature_eggnation.domain.use_case.individual_use_cases.user_use_case.game_logic_use_case

import android.content.Context
import com.applicnation.eggnation.feature_eggnation.domain.repository.InternetRepository
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject

class InternetConnectivityUC @Inject constructor(
    private val internet: InternetRepository
) {

    /**
     * TODO - add documentation
     */
    operator fun invoke(context: Context): Boolean {
        try {
            return internet.isOnline(context)
        } catch (e: Exception) {
            Timber.e("INTERNET: Failed to fetch internet status. Unexpected Error Occurred  --> $e")
            return false
        }
    }
}