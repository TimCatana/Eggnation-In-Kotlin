package com.applicnation.eggnation.feature_eggnation.presentation.navigation

sealed class PolicyScreen(val route: String) {
    object PrivacyPolicy: PolicyScreen(route = "privacyPolicyScreen")
    object TermsOfService: PolicyScreen(route = "termsOfServiceScreen")
}