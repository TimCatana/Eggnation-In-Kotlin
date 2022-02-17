package com.applicnation.eggnation.feature_eggnation.domain.use_case.authentication_use_case

import com.applicnation.eggnation.feature_eggnation.domain.repository.AuthenticationRepository
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import timber.log.Timber
import javax.inject.Inject

class SignInUserUC @Inject constructor(
    private val authenticator: AuthenticationRepository
) {
    /**
     * Signs the user into their account given their email and password
     * @param email The user email (should be sanitized by now)
     * @param password The user password (should be sanitized by now)
     * @exception FirebaseAuthInvalidCredentialsException The email address is badly formatted (password should always be valid)
     * @exception FirebaseAuthInvalidUserException The user does not exist in Firebase Authentication (the user was either deleted or did not sign up yet)
     * @exception Exception All exceptions thrown from this catch block are UNEXPECTED
     */
    suspend operator fun invoke(email: String, password: String) {
        try {
            authenticator.signInUser(email, password)
            Timber.i("SUCCESS: User signed in")
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            Timber.e("Failed to sign user in: Email is invalid (not formatted correctly) --> $e")
//            throw FailedToSignInException("Failed to sign in!")
        } catch (e: FirebaseAuthInvalidUserException) {
            Timber.e("Failed to sign user in: User does not exist --> $e")
//            throw FailedToSignInException("Failed to sign in!")
        } catch (e: Exception) {
            Timber.wtf("Failed to sign user in: An unexpected error occurred --> $e")
//            throw FailedToSignInException("Failed to sign in!")
        }
    }
}
