package com.applicnation.eggnation.feature_eggnation.presentation.navigation

sealed class PolicyScreen(val route: String) {
    object PrivacyPolocy: PolicyScreen(route = "privacyPolicyScreen")
    object TermsOfService: PolicyScreen(route = "termsOfServiceScreen")
}