package com.applicnation.eggnation.feature_eggnation.domain.use_case.authentication_use_case

import com.applicnation.eggnation.feature_eggnation.domain.repository.AuthenticationRepository
import com.applicnation.eggnation.feature_eggnation.domain.util.FailedToSendPasswordResetEmailException
import com.google.firebase.auth.FirebaseAuthEmailException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject

class SendUserPasswordResetEmailUC @Inject constructor(
    private val authenticator: AuthenticationRepository
) {

    /**
     * Sends the user a verification email. The email should contain a deep link to the VerifyEmailScreen.
     * If user is not logged in when they reach the VerifyEmailScreen, then do not do anything. That is a security hazard
     * @param email The email to send password reset link to
     * @exception FirebaseAuthInvalidUserException TODO
     * @exception FirebaseAuthEmailException Password reset email failed to send
     * @exception Exception All exceptions thrown from this catch block are UNEXPECTED
     */
    suspend operator fun invoke(email: String) {
        try {
            authenticator.sendPasswordResetEmail(email)
            Timber.i("SUCCESS: User password reset")
            // TODO - propogate message saying that the email was sent
        } catch (e: FirebaseAuthInvalidUserException) {
            Timber.e("Failed to send password reset email: No user corresponding to email address --> $e")
            throw FailedToSendPasswordResetEmailException("Failed to send password reset email. Is email valid?")
        } catch (e: FirebaseAuthEmailException) {
            Timber.e("Failed to send verification email: --> $e")
            throw FailedToSendPasswordResetEmailException("Failed to send password reset email. Is email valid?")
        } catch (e: Exception) {
            Timber.e("Failed to send password reset email: An unexpected error occurred --> $e")
            throw FailedToSendPasswordResetEmailException("Failed to send password reset email. Is email valid?")
        }
    }
}
