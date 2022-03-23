package com.applicnation.eggnation.feature_eggnation.domain.use_case.individual_use_cases.user_use_case.user_account_use_case

import com.applicnation.eggnation.feature_eggnation.domain.repository.AuthenticationRepository
import com.applicnation.eggnation.util.Resource
import com.google.firebase.auth.FirebaseAuthEmailException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
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
    // TODO - add firebase network error in try catch
    operator fun invoke(): Flow<Resource<String>> = flow {
        emit(Resource.Loading<String>())

        if (authenticator.getUserLoggedInStatus() == null) {
            Timber.wtf("!!!! Something is wrong, the user is in game nav graph but not logged in")
            emit(Resource.Error<String>("An unexpected error occurred"))
            return@flow
        }

        try {
            authenticator.sendVerificationEmail()
        } catch (e: FirebaseAuthEmailException) {
            Timber.e("Failed to send verification email: Either email is formatted badly or email does not exists --> $e")
            emit(Resource.Error<String>("Failed to send email"))
            return@flow
        } catch (e: Exception) {
            Timber.e("Failed to send verification email: An unexpected error occurred --> $e")
            emit(Resource.Error<String>("Failed to send email"))
            return@flow
        }

        emit(Resource.Success<String>(message = "Email Sent Successfully. If you cannot find it, check your spam folder"))
    }.flowOn(Dispatchers.IO)
}
