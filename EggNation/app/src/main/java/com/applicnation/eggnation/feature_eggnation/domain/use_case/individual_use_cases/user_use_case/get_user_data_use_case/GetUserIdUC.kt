package com.applicnation.eggnation.feature_eggnation.domain.use_case.individual_use_cases.user_use_case.get_user_data_use_case

import com.applicnation.eggnation.feature_eggnation.domain.repository.AuthenticationRepository
import timber.log.Timber
import javax.inject.Inject

class GetUserIdUC @Inject constructor(
    private val authenticator: AuthenticationRepository
) {
    /**
     * Get's the user's uid from Firebase Authentication
     * @note authenticator.getUserId() will never return null because the current user logged in status is checked beforehand
     */
    operator fun invoke(): String {
        val userId = authenticator.getUserId()
        Timber.d("userId: $userId")

        if (userId == null) {
            Timber.wtf("!!!! FIREBASE AUTH: UserId is null. This should never happen")
            return ""
        } else {
            return userId
        }
    }
}
