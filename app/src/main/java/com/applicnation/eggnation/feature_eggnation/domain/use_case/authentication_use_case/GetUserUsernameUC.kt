package com.applicnation.eggnation.feature_eggnation.domain.use_case.authentication_use_case

import com.applicnation.eggnation.feature_eggnation.domain.repository.AuthenticationRepository
import javax.inject.Inject

class GetUserUsernameUC @Inject constructor(
    private val authenticator: AuthenticationRepository
) {
    /**
     * Get's the user's username from Firebase Authentication
     * @note authenticator.getUserUsername() will never return null because the current user logged in status is checked beforehand
     */
    operator fun invoke(): String? {
        if (authenticator.getUserLoggedInStatus() == null) {
            throw Exception()
            // TODO - throw exception? I ned t
        } else {
            return authenticator.getUserUsername()!!
        }
    }
}
