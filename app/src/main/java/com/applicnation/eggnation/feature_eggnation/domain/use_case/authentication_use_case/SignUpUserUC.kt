package com.applicnation.eggnation.feature_eggnation.domain.use_case.authentication_use_case

import com.applicnation.eggnation.feature_eggnation.domain.repository.AuthenticationRepository
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import timber.log.Timber
import javax.inject.Inject

class SignUpUserUC @Inject constructor(
    private val authenticator: AuthenticationRepository
) {
    /**
     * Creates a new account for the user.
     * Adds the user to firebase auth console
     * @param email (String) The user email (should be valid and sanitized by now)
     * @param password (String) The user password (should be valid and sanitized by now)
     *                 should be greater than 8 characters contain lowercase letters, uppercase letters and numbers. Should contain no whitespace
     * @exception FirebaseAuthInvalidCredentialsException The email address is malformed SHOULD NEVER BE THROWN!!! (because the string should already have been sanitized and validated in frontend)
     * @exception FirebaseAuthWeakPasswordException The use password is less that 6 chars. SHOULD NEVER BE THROWN!!! (because the string should already have been sanitized and validated in frontend)
     * @exception FirebaseAuthUserCollisionException The user already exists (the email is already in use)
     * @exception Exception All exceptions caught in this block are UNEXPECTED and should not ever exist
     */
    suspend operator fun invoke(email: String, password: String) {
        // TODO - send a something to UI saying success or failure
        try {
            authenticator.signUpUser(email, password)
            Timber.i("SUCCESS: User signed up (registered)")
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            Timber.wtf("Failed to sign user up (register): Email is malformed --> $e")
//            throw FailedToSignUpException("Please enter a valid email address!")
        } catch (e: FirebaseAuthWeakPasswordException) {
            Timber.wtf("Failed to sign user up (register): Weak Password (needs to be greater than 6 characters) --> $e")
//            throw FailedToSignUpException("Please enter a valid password!")
        } catch (e: FirebaseAuthUserCollisionException) {
            Timber.e("Failed to sign user up (register): User already exists: --> $e ")
//            throw FailedToSignUpException("User already exists!")
        } catch (e: Exception) {
            Timber.wtf("Failed to sign user up (register): An unexpected error occurred --> $e")
//            throw FailedToSignUpException("something went wrong. Please try again")
        }
    }
}
