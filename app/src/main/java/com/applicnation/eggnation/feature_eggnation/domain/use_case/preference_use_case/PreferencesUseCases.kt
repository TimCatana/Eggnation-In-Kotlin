package com.applicnation.eggnation.feature_eggnation.domain.use_case.preference_use_case

data class PreferencesUseCases(
    val preferencesGet: PreferencesGet,
    val preferencesUpdateSelectedSkin: PreferencesUpdateSelectedSkin,
    val preferencesUpdateReceivesNotifications: PreferencesUpdateReceivesNotifications
)