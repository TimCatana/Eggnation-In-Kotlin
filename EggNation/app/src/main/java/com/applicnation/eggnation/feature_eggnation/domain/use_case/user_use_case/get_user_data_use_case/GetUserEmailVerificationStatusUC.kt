package com.applicnation.eggnation.feature_eggnation.domain.use_case.user_use_case.get_user_data_use_case

import com.applicnation.eggnation.feature_eggnation.domain.repository.AuthenticationRepository
import timber.log.Timber
import javax.inject.Inject

class GetUserEmailVerificationStatusUC @Inject constructor(
    private val authenticator: AuthenticationRepository
) {
    /**
     * Get's the user's email verification status from Firebase Authentication
     * @note authenticator.getUserEmailVerificationStatus() will never return null because the current user logged in status is checked beforehand
     */
    operator fun invoke(): Boolean {
        val emailVerificationStatus = authenticator.getUserEmailVerificationStatus()

        Timber.i("emailVerificationStatue: $emailVerificationStatus")

        if (emailVerificationStatus == null) {
            // TODO - this is a very bad case... need to do something
            Timber.wtf("!!! User is signed out while trying to go to settings screen")
            return false
        } else {
            return emailVerificationStatus
        }
    }
}
