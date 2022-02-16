package com.applicnation.eggnation.feature_eggnation.domain.use_case.preference_use_case

data class AllPreferencesUseCases(
    val getSelectedSkinPrefUC: GetSelectedSkinPrefUC,
    val getTapCountPrefUC: GetTapCountPrefUC,
    val getReceivesNotificationsPrefUC: GetReceivesNotificationsPrefUC,
    val getLastResetTimePrefUC: GetLastResetTimePrefUC,

    val updateSelectedSkinPrefUC: UpdateSelectedSkinPrefUC,
    val updateTapCountPrefUC: UpdateTapCountPrefUC,
    val updateReceivesNotificationsPrefUC: UpdateReceivesNotificationsPrefUC,
    val updateLastResetTimePrefUC: UpdateLastResetTimePrefUC,

    val decrementTapCountPrefUC: DecrementTapCountPrefUC
)
