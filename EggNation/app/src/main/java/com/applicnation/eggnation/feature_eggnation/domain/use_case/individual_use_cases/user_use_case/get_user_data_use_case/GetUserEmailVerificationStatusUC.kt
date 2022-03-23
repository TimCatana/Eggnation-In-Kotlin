package com.applicnation.eggnation.feature_eggnation.domain.use_case.individual_use_cases.user_use_case.get_user_data_use_case

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
        Timber.d("emailVerificationStatus: $emailVerificationStatus")

        if (emailVerificationStatus == null) {
            Timber.wtf("!!!! FIREBASE AUTH: User emailVerificationStatus is null. This should never happen")
            return false
        } else {
            return emailVerificationStatus
        }
    }
}
