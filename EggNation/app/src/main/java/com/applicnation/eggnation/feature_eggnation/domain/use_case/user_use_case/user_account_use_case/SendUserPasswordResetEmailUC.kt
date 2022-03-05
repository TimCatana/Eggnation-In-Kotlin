package com.applicnation.eggnation.feature_eggnation.domain.use_case.user_use_case.user_account_use_case

import com.applicnation.eggnation.feature_eggnation.domain.repository.AuthenticationRepository
import com.applicnation.eggnation.feature_eggnation.domain.util.FailedToSendPasswordResetEmailException
import com.applicnation.eggnation.util.Resource
import com.google.firebase.auth.FirebaseAuthEmailException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
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
     * @exception FirebaseAuthInvalidUserException
     * @exception FirebaseAuthEmailException
     * @exception Exception All exceptions thrown from this catch block are UNEXPECTED
     */
    operator fun invoke(email: String): Flow<Resource<String>> = flow {
        emit(Resource.Loading<String>())

        try {
            authenticator.sendPasswordResetEmail(email)
            Timber.i("SUCCESS: User password reset")
            emit(Resource.Success<String>(message = "Email sent successfully! If you can't find it, check your spam folder"))
        } catch (e: FirebaseAuthInvalidUserException) {
            Timber.e("Failed to send password reset email: No user corresponding to email address --> $e")
            emit(Resource.Error<String>("Email does not exist, did you register an account yet?"))
        } catch (e: FirebaseAuthEmailException) {
            Timber.e("Failed to send verification email: --> $e")
            emit(Resource.Error<String>("Failed to send email!"))
        } catch (e: Exception) {
            Timber.e("Failed to send password reset email: An unexpected error occurred --> $e")
            emit(Resource.Error<String>("An unexpected error occurred, Failed to send email"))
        }
    }.flowOn(Dispatchers.IO)
}
