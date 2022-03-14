package com.applicnation.eggnation.feature_eggnation.domain.use_case.user_use_case.user_account_use_case

import com.applicnation.eggnation.feature_eggnation.domain.repository.AuthenticationRepository
import com.applicnation.eggnation.feature_eggnation.domain.util.FailedToSendPasswordResetEmailException
import com.applicnation.eggnation.util.Resource
import com.google.firebase.FirebaseNetworkException
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
     * Sends a password reset email to the email address provided
     * @param email The email to send password reset link to
     * @exception FirebaseAuthInvalidUserException
     * @exception FirebaseAuthEmailException
     * @exception FirebaseNetworkException
     * @exception Exception All exceptions thrown from this catch block are UNEXPECTED
     */
    operator fun invoke(email: String): Flow<Resource<String>> = flow {
        emit(Resource.Loading<String>())

        try {
            authenticator.sendPasswordResetEmail(email)
            Timber.i("FIREBASE AUTH SUCCESS: User password reset")
            emit(Resource.Success<String>(message = "Email sent successfully! If you can't find it, check your spam folder"))
        } catch (e: FirebaseAuthInvalidUserException) {
            Timber.e("FIREBASE AUTH: FAILED to send password reset email: No user corresponding to email address --> $e")
            emit(Resource.Error<String>("Email does not exist, did you register an account yet?"))
        } catch (e: FirebaseAuthEmailException) {
            Timber.e("FIREBASE AUTH: FAILED to send verification email: --> $e")
            emit(Resource.Error<String>("Failed to send email!"))
        } catch (e: FirebaseNetworkException) {
            Timber.e("FIREBASE AUTH: FAILED to sign user up (register): Not connected to internet --> $e")
            emit(Resource.Error<String>("Make sure you are connected to the internet"))
        } catch (e: Exception) {
            Timber.e("FIREBASE AUTH: FAILED to send password reset email: An unexpected error occurred --> $e")
            emit(Resource.Error<String>("An unexpected error occurred, Failed to send email"))
        }
    }.flowOn(Dispatchers.IO)
}
