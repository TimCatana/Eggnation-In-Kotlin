package com.applicnation.eggnation.feature_eggnation.presentation.auth.register

sealed class RegisterScreenEvent {
    data class EnteredEmail(val value: String) : RegisterScreenEvent()
    data class EnteredPassword(val value: String) : RegisterScreenEvent()
    data class EnteredConfirmPassword(val value: String) : RegisterScreenEvent()
    object SignUp: RegisterScreenEvent()
}
