package com.applicnation.eggnation.feature_eggnation.domain.use_case.screen_use_cases.auth

import com.applicnation.eggnation.feature_eggnation.domain.use_case.individual_use_cases.user_use_case.user_account_use_case.SignInUserUC

data class LoginScreenUseCases(
    val signInUserUC: SignInUserUC
)