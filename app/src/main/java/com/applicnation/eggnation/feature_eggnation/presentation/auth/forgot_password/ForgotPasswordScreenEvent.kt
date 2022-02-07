package com.applicnation.eggnation.feature_eggnation.presentation.auth.forgot_password

sealed class ForgotPasswordScreenEvent {
    data class EnteredEmail(val value: String): ForgotPasswordScreenEvent()
    data class ResetPassword(val email: String): ForgotPasswordScreenEvent()
}