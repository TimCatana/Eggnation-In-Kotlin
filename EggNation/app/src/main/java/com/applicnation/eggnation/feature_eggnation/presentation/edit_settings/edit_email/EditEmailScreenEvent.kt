package com.applicnation.eggnation.feature_eggnation.presentation.edit_settings.edit_email

sealed class EditEmailScreenEvent {
    data class EnteredEmail(val value: String): EditEmailScreenEvent()
    data class EnteredPassword(val value: String): EditEmailScreenEvent()
    object ShowPasswordModal : EditEmailScreenEvent()
    object HidePasswordModal : EditEmailScreenEvent()
    object UpdateEmail: EditEmailScreenEvent()
}
