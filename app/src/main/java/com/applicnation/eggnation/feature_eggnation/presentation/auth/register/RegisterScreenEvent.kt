package com.applicnation.eggnation.feature_eggnation.presentation.auth.register

sealed class RegisterScreenEvent {
    data class EnteredUserName(val value: String) : RegisterScreenEvent()
    data class EnteredEmail(val value: String) : RegisterScreenEvent()
    data class EnteredPassword(val value: String) : RegisterScreenEvent()
    data class EnteredConfirmPassword(val value: String) : RegisterScreenEvent()
    /**
     * parameters will depend on what service
     * you are using to keep track of user accounts
     * */
    data class Register(
        val username: String,
        val email: String,
        val password: String
    ) : RegisterScreenEvent()
}