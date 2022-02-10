package com.applicnation.eggnation.feature_eggnation.domain.use_case.preference_use_case

data class PreferencesUseCases(
    val preferencesGetTapCount: PreferencesGetTapCount,
    val preferencesUpdateTapCount: PreferencesUpdateTapCount,
    val preferencesGetSelectedSkin: PreferencesGetSelectedSkin,
    val preferencesUpdateSelectedSkin: PreferencesUpdateSelectedSkin,
    val preferencesGetLastResetTime: PreferencesGetLastResetTime,
    val preferencesUpdateLastResetTime: PreferencesUpdateLastResetTime,
    val preferencesGetReceivesNotifications: PreferencesGetReceivesNotifications,
    val preferencesUpdateReceivesNotifications: PreferencesUpdateReceivesNotifications,
    val preferencesDecrementTapCount: PreferencesDecrementTapCount
)