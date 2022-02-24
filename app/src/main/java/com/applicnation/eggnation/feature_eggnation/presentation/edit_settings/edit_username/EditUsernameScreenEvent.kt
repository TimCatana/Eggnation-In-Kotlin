package com.applicnation.eggnation.feature_eggnation.presentation.edit_settings.edit_username

sealed class EditUsernameScreenEvent {
    data class EnteredUsername(val value: String): EditUsernameScreenEvent()
    data class EnteredPassword(val value: String): EditUsernameScreenEvent()
    data class UpdateUsername(val newUsername: String, val password: String): EditUsernameScreenEvent()
}