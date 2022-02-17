package com.applicnation.eggnation.feature_eggnation.domain.use_case.user_use_case.user_account_use_case

import com.applicnation.eggnation.feature_eggnation.domain.repository.AuthenticationRepository
import com.google.firebase.auth.FirebaseAuthEmailException
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject

class SendUserVerificationEmailUC @Inject constructor(
    private val authenticator: AuthenticationRepository
) {
    /**
     * Sends a verification email to the email that the current user is using.
     * If they inputted the incorrect email when they registered, they have the option to change their email in settings.
     * @exception FirebaseAuthEmailException Verification email failed to send
     * @exception Exception All exceptions thrown from this catch block are UNEXPECTED
     */
    suspend operator fun invoke() {

        if (authenticator.getUserLoggedInStatus() == null) {
            throw Exception()
            // TODO - throw exception?
            // TODO - this is a very bad situation to be in
        }

        try {
            authenticator.sendVerificationEmail()
            // TODO - propogate message saying that the email was sent
        } catch (e: FirebaseAuthEmailException) {
            // TODO - need to propogate some error to the UI
            Timber.e("Failed to send verification email: Either email is formatted badly or email does not exists --> $e")
        } catch (e: Exception) {
            // TODO - need to propogate some error to the UI
            Timber.e("Failed to send verification email: An unexpected error occurred --> $e")
        }
    }
}
