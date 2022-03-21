package com.applicnation.eggnation.feature_eggnation.domain.use_case.user_use_case.game_logic_use_case

import android.content.Context
import com.applicnation.eggnation.feature_eggnation.domain.repository.DatabaseRepository
import com.applicnation.eggnation.feature_eggnation.domain.repository.InternetRepository
import javax.inject.Inject

class InternetConnectivityUC @Inject constructor(
    private val internet: InternetRepository
) {

    operator fun invoke(context: Context): Boolean {
        return internet.isOnline(context)
    }
}