package com.applicnation.eggnation.feature_eggnation.domain.use_case.authentication_use_case

import com.applicnation.eggnation.feature_eggnation.domain.repository.AuthenticationRepository
import javax.inject.Inject

class UpdateUserEmailAddressUC @Inject constructor(
    private val authenticator: AuthenticationRepository
) {
    suspend operator fun invoke(email: String, password: String, newEmail: String) {
        // TODO - maybe add a try catch here? to propogate potential thrown errors
        authenticator.updateUserEmailAddress(email, password, newEmail)
    }
}
