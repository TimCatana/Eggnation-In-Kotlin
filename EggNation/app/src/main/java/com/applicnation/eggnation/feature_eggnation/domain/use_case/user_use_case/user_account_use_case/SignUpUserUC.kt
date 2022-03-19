package com.applicnation.eggnation.feature_eggnation.domain.use_case.user_use_case.user_account_use_case

import com.applicnation.eggnation.feature_eggnation.domain.repository.AuthenticationRepository
import com.applicnation.eggnation.util.Resource
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import javax.inject.Inject

class SignUpUserUC @Inject constructor(
    private val authenticator: AuthenticationRepository,
) {
    /**
     * Creates a new account for the user.
     * Adds the user to Firebase Authentication.
     * Adds the user to database.
     * @param email (String) The user email (should be valid and sanitized by now)
     * @param password (String) The user password (should be valid and sanitized by now)
     *                 should be greater than 8 characters contain lowercase letters, uppercase letters and numbers. Should contain no whitespace
     * @exception FirebaseAuthInvalidCredentialsException SHOULD NEVER BE THROWN!!!
     * @exception FirebaseAuthWeakPasswordException SHOULD NEVER BE THROWN!!!
     * @exception FirebaseNetworkException
     * @exception FirebaseAuthUserCollisionException
     * @exception Exception All exceptions caught in this block are UNEXPECTED and should not ever occur
     * @note Read the log messages below to see what each exception means (it is too messy to put all the info in this comment)
     */
    operator fun invoke(email: String, password: String): Flow<Resource<String>> =
        flow {
            emit(Resource.Loading<String>())

            try {
                authenticator.signUpUser(email, password)
                Timber.i("FIREBASE AUTH: SUCCESS - User registered")
            } catch (e: FirebaseAuthInvalidCredentialsException) {
                Timber.wtf("FIREBASE AUTH: FAILED to sign user up (register): Email is malformed --> $e")
                emit(Resource.Error<String>(message = "Please enter a valid email address"))
                return@flow
            } catch (e: FirebaseAuthWeakPasswordException) {
                Timber.wtf("FIREBASE AUTH: FAILED to sign user up (register): Weak Password (needs to be greater than 6 characters) --> $e")
                emit(Resource.Error<String>(message = "Password must be at least 6 characters long"))
                return@flow
            } catch (e: FirebaseNetworkException) {
                Timber.e("FIREBASE AUTH: FAILED to sign user up (register): Not connected to internet --> $e")
                emit(Resource.Error<String>(message = "Make sure you're connected to the internet"))
                return@flow
            } catch (e: FirebaseAuthUserCollisionException) {
                Timber.e("FIREBASE AUTH: FAILED to sign user up (register): User already exists: --> $e ")
                emit(Resource.Error<String>(message = "Email already in use"))
                return@flow
            } catch (e: Exception) {
                Timber.e("FIREBASE AUTH: FAILED to sign user up (register): An unexpected error occurred --> $e")
                emit(Resource.Error<String>(message = "An unexpected error occurred"))
                return@flow
            }

            // @NOTE User is added to database via a cloud function
            emit(Resource.Success<String>(message = "Registered Successfully"))
        }.flowOn(Dispatchers.IO)
}
