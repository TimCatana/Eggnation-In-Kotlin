package com.applicnation.eggnation.feature_eggnation.domain.use_case.authentication_use_case

import com.applicnation.eggnation.feature_eggnation.domain.repository.AuthenticationRepository
import com.applicnation.eggnation.feature_eggnation.domain.util.FailedToSignOutException
import timber.log.Timber
import javax.inject.Inject

class SignOutUserUC @Inject constructor(
    private val authenticator: AuthenticationRepository
) {

    /**
     * Signs the user out of their account on their given device
     * @exception Exception All exceptions thrown from this catch block are UNEXPECTED
     */
    operator fun invoke() {
        try {
            authenticator.signOutUser()
            Timber.i("SUCCESS: User signed out")
        } catch (e: Exception) {
            Timber.wtf("Failed to sign user out: Unexpected exception $e")
//            throw FailedToSignOutException("something went wrong. Please try again")
        }
    }
}
