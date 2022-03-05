package com.applicnation.eggnation.feature_eggnation.presentation.edit_settings.edit_password

sealed class EditPasswordScreenEvent {
    data class EnteredPassword(val value: String) : EditPasswordScreenEvent()
    data class EnteredConfirmPassword(val value: String) : EditPasswordScreenEvent()
    data class UpdatePassword(val newPassword: String): EditPasswordScreenEvent()
}