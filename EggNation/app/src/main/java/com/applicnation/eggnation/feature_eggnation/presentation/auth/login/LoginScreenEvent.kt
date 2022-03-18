package com.applicnation.eggnation.feature_eggnation.presentation.auth.login

sealed class LoginScreenEvent{
    data class EnteredEmail(val value: String): LoginScreenEvent()
    data class EnteredPassword(val value: String): LoginScreenEvent()
    object SignIn: LoginScreenEvent()
}
