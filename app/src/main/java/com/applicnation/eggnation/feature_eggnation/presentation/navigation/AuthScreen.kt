package com.applicnation.eggnation.feature_eggnation.presentation.navigation

sealed class AuthScreen(val route: String) {
    object Login: AuthScreen(route = "loginScreen")
    object Register: AuthScreen(route = "registerScreen")
    object ForgotPassword: AuthScreen(route = "forgotPasswordScreen")
}
