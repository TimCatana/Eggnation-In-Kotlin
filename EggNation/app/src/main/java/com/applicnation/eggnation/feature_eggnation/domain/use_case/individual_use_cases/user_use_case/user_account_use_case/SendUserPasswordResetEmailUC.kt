package com.applicnation.eggnation.feature_eggnation.domain.use_case.individual_use_cases.user_use_case.user_account_use_case

import com.applicnation.eggnation.feature_eggnation.domain.repository.AuthenticationRepository
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
            Timber.i("FIREBASE AUTH: SUCCESS - User password reset email sent")
        } catch (e: FirebaseAuthInvalidUserException) {
            Timber.e("FIREBASE AUTH: FAILED to send password reset email: No user corresponding to email address --> $e")
            emit(Resource.Error<String>(message = "Email not found. Did you register an account yet?"))
            return@flow
        } catch (e: FirebaseAuthEmailException) {
            Timber.e("FIREBASE AUTH: FAILED to send password reset email: --> $e")
            emit(Resource.Error<String>(message = "Something went wrong on our side"))
            return@flow
        } catch (e: FirebaseNetworkException) {
            Timber.e("FIREBASE AUTH: FAILED to send  password reset email: Not connected to internet --> $e")
            emit(Resource.Error<String>(message = "Make sure you are connected to the internet"))
            return@flow
        } catch (e: Exception) {
            Timber.e("FIREBASE AUTH: FAILED to send password reset email: An unexpected error occurred --> $e")
            emit(Resource.Error<String>(message = "An unexpected error occurred"))
            return@flow
        }

        emit(Resource.Success<String>(message = "Email sent successfully! If you can't find it, check your spam folder"))
    }.flowOn(Dispatchers.IO)
}
