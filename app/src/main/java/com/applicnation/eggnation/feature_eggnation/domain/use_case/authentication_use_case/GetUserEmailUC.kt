package com.applicnation.eggnation.feature_eggnation.domain.use_case.authentication_use_case

import com.applicnation.eggnation.feature_eggnation.domain.repository.AuthenticationRepository
import javax.inject.Inject

class GetUserEmailUC @Inject constructor(
    private val authenticator: AuthenticationRepository
) {
    /**
     * Get's the user's email address from Firebase Authentication
     * @note authenticator.getUserEmail() will never return null because the current user logged in status is checked beforehand
     */
    operator fun invoke(): String {
        if (authenticator.getUserLoggedInStatus() == null) {
            return "very bad case"
            // TODO - throw exception? I ned t
        } else {
            return authenticator.getUserEmail()!!
        }
    }
}
