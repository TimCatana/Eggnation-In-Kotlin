package com.applicnation.eggnation.feature_eggnation.domain.use_case.user_use_case.get_user_data_use_case

import com.applicnation.eggnation.feature_eggnation.domain.repository.AuthenticationRepository
import javax.inject.Inject

class GetUserEmailVerificationStatusUC @Inject constructor(
    private val authenticator: AuthenticationRepository
) {
    /**
     * Get's the user's email verification status from Firebase Authentication
     * @note authenticator.getUserEmailVerificationStatus() will never return null because the current user logged in status is checked beforehand
     */
    operator fun invoke(): Boolean {
        if (authenticator.getUserLoggedInStatus() == null) {
            return false
            // TODO - throw exception? I ned t
        } else {
            return authenticator.getUserEmailVerificationStatus()!!
        }
    }
}
