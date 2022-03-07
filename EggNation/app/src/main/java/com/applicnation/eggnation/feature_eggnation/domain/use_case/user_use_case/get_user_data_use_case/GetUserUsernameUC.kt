package com.applicnation.eggnation.feature_eggnation.domain.use_case.user_use_case.get_user_data_use_case

import com.applicnation.eggnation.feature_eggnation.domain.repository.AuthenticationRepository
import timber.log.Timber
import javax.inject.Inject

class GetUserUsernameUC @Inject constructor(
    private val authenticator: AuthenticationRepository
) {
    /**
     * Get's the user's username from Firebase Authentication
     * @note authenticator.getUserUsername() will never return null because the current user logged in status is checked beforehand
     */
    operator fun invoke(): String {
        val username = authenticator.getUserUsername()

        Timber.wtf("kfsfodlfoflo ${authenticator.getUserUsername()}")

        if (username == null) {
            // TODO - this is a very bad case... need to do something
            Timber.wtf("!!! User is signed out while trying to go to settings screen")
            return ""
        } else {
            return username
        }
    }
}
