package com.applicnation.eggnation.feature_eggnation.domain.use_case

import com.applicnation.eggnation.feature_eggnation.domain.use_case.preference_use_case.*

data class AllPreferencesUseCases(
    val getTapCountPrefUC: GetTapCountPrefUC,
    val getLastResetTimePrefUC: GetLastResetTimePrefUC,

    val updateTapCountPrefUC: UpdateTapCountPrefUC,
    val updateLastResetTimePrefUC: UpdateLastResetTimePrefUC,

    val decrementTapCountPrefUC: DecrementTapCountPrefUC
)
