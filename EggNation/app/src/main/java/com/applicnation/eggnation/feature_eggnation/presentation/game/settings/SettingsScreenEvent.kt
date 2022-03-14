package com.applicnation.eggnation.feature_eggnation.presentation.game.settings

import com.applicnation.eggnation.feature_eggnation.data.remote.firebase.AdMob

sealed class SettingsScreenEvent {
    data class EnteredPassword(val value: String) : SettingsScreenEvent()
    data class ShowPasswordModel(val showPasswordModel: Boolean) : SettingsScreenEvent()
    object SignOut : SettingsScreenEvent()
    data class DeleteAccount(val password: String): SettingsScreenEvent()
}