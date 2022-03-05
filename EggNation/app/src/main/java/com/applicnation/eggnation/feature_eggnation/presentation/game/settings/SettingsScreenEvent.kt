package com.applicnation.eggnation.feature_eggnation.presentation.game.settings

import com.applicnation.eggnation.feature_eggnation.data.remote.firebase.AdMob

sealed class SettingsScreenEvent {
    object SignOut : SettingsScreenEvent()
    data class EnteredPassword(val value: String) : SettingsScreenEvent()
    data class EditProfile(val password: String) : SettingsScreenEvent()
    data class ShowPasswordModel(val showPasswordModel: Boolean, val navScreen: String) : SettingsScreenEvent()
}