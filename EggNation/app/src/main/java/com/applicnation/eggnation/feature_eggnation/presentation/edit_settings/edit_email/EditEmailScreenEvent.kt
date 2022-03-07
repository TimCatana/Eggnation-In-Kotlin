package com.applicnation.eggnation.feature_eggnation.presentation.edit_settings.edit_email

import com.applicnation.eggnation.feature_eggnation.presentation.game.settings.SettingsScreenEvent

sealed class EditEmailScreenEvent {
    data class EnteredEmail(val value: String): EditEmailScreenEvent()
    data class EnteredPassword(val value: String): EditEmailScreenEvent()
    data class UpdateEmail(val newEmail: String): EditEmailScreenEvent()
    data class ShowPasswordModel(val showPasswordModel: Boolean, val navScreen: String) : EditEmailScreenEvent()

}