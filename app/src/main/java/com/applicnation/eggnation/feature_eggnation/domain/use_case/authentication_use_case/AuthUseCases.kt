package com.applicnation.eggnation.feature_eggnation.domain.use_case.authentication_use_case

data class AuthUseCases(
    val userSignIn: UserSignIn,
    val userSignUp: UserSignUp,
    val userPasswordReset: UserPasswordReset
)
