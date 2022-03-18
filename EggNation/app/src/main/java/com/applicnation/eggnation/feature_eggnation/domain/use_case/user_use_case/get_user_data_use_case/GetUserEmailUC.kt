package com.applicnation.eggnation.feature_eggnation.domain.use_case.user_use_case.get_user_data_use_case

import com.applicnation.eggnation.feature_eggnation.domain.repository.AuthenticationRepository
import timber.log.Timber
import javax.inject.Inject

class GetUserEmailUC @Inject constructor(
    private val authenticator: AuthenticationRepository
) {
    /**
     * Get's the user's email address from Firebase Authentication
     * @note authenticator.getUserEmail() will never return null because the current user logged in status is checked beforehand
     */
    operator fun invoke(): String {
        val email = authenticator.getUserEmail()
        Timber.d("email: $email")


        if (email == null) {
            Timber.wtf("!!!! FIREBASE AUTH: User email is null. This should never happen")
            return ""
        } else {
            return email
        }
    }
}
