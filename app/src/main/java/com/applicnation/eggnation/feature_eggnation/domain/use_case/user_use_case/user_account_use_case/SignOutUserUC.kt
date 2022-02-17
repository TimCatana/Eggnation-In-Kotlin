package com.applicnation.eggnation.feature_eggnation.domain.use_case.user_use_case.user_account_use_case

import com.applicnation.eggnation.feature_eggnation.domain.repository.AuthenticationRepository
import timber.log.Timber
import javax.inject.Inject

class SignOutUserUC @Inject constructor(
    private val authenticator: AuthenticationRepository
) {
    // TODO - clean up documentation

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
        }
    }
}
