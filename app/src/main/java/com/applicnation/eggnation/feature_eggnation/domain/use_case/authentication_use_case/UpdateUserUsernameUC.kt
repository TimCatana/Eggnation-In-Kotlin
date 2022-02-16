package com.applicnation.eggnation.feature_eggnation.domain.use_case.authentication_use_case

import com.applicnation.eggnation.feature_eggnation.domain.repository.AuthenticationRepository
import javax.inject.Inject

class UpdateUserUsernameUC @Inject constructor(
    private val authenticator: AuthenticationRepository
) {
    suspend operator fun invoke(newUsername: String) {
        // TODO - maybe add a try catch here? to propogate potential thrown errors
        authenticator.updateUserUsername(newUsername)
    }
}
