package com.applicnation.eggnation.feature_eggnation.presentation.edit_settings.edit_password

import com.applicnation.eggnation.feature_eggnation.presentation.edit_settings.edit_email.EditEmailScreenEvent

sealed class EditPasswordScreenEvent {
    data class EnteredValidationPassword(val value: String): EditPasswordScreenEvent()
    data class ShowPasswordModel(val showPasswordModel: Boolean, val navScreen: String) : EditPasswordScreenEvent()


    data class EnteredPassword(val value: String) : EditPasswordScreenEvent()
    data class EnteredConfirmPassword(val value: String) : EditPasswordScreenEvent()
    data class UpdatePassword(val newPassword: String): EditPasswordScreenEvent()
}