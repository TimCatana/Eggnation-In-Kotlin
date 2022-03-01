package com.applicnation.eggnation.feature_eggnation.presentation.auth.login

sealed class LoginScreenEvent{
    data class EnteredEmail(val value: String): LoginScreenEvent()
    data class EnteredPassword(val value: String): LoginScreenEvent()
    data class SignIn(val email: String, val password: String): LoginScreenEvent()
    // TODO - maybe do some focus changing stuff for the text fields
}