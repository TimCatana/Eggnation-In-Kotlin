package com.applicnation.eggnation.feature_eggnation.domain.use_case.user_use_case.user_account_use_case

import com.applicnation.eggnation.feature_eggnation.domain.repository.AuthenticationRepository
import com.applicnation.eggnation.feature_eggnation.domain.repository.DatabaseRepository
import com.applicnation.eggnation.util.Resource
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class SignUpUserUC @Inject constructor(
    private val authenticator: AuthenticationRepository,
    private val repository: DatabaseRepository
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
     * @exception FirebaseFirestoreException
     * @exception Exception All exceptions caught in this block are UNEXPECTED and should not ever occur
     * @note Read the log messages below to see what each exception means (it is too messy to put all the info in this comment)
     */
    operator fun invoke(email: String, password: String, username: String): Flow<Resource<String>> =
        flow {
            emit(Resource.Loading<String>())

            // TODO - put email to lowercase

            /**
             * Adding the user to Firebase Authentication.
             * @exception FirebaseAuthInvalidCredentialsException
             * @exception FirebaseAuthWeakPasswordException
             * @exception FirebaseAuthUserCollisionException
             * @exception FirebaseNetworkException
             * @exception Exception
             */
            try {
                authenticator.signUpUser(email, password)
                Timber.i("SUCCESS Firebase Authentication: User signed up (registered)")
            } catch (e: FirebaseAuthInvalidCredentialsException) {
                Timber.wtf("Firebase Authentication: Failed to sign user up (register): Email is malformed --> $e")
                emit(Resource.Error<String>("An unexpected error occurred"))
                return@flow
            } catch (e: FirebaseAuthWeakPasswordException) {
                Timber.wtf("Firebase Authentication: Failed to sign user up (register): Weak Password (needs to be greater than 6 characters) --> $e")
                emit(Resource.Error<String>("An unexpected error occurred"))
                return@flow
            } catch (e: FirebaseNetworkException) {
                Timber.e("Firebase Authentication: Failed to sign user up (register): Not connected to internet --> $e")
                emit(Resource.Error<String>("Registration failed! Make sure you are connected to the internet"))
                return@flow
            } catch (e: FirebaseAuthUserCollisionException) {
                Timber.e("Firebase Authentication: Failed to sign user up (register): User already exists: --> $e ")
                emit(Resource.Error<String>("Email already in use!"))
                return@flow
            } catch (e: Exception) {
                Timber.e("Firebase Authentication: Failed to sign user up (register): An unexpected error occurred --> $e")
                emit(Resource.Error<String>("An unexpected error occurred"))
                return@flow
            }

            /**
             * Fetching the newly created user's Id from Firebase Authentication.
             * @note if the userId is null, something went horribly wrong. Therefore sign the user
             *       in and try again. If that fails, then rip... THIS SHOULD NEVER HAPPEN!!!
             * @exception Exception
             */
            var userId = authenticator.getUserId()

            if (userId == null) {
                try {
                    authenticator.signInUser(email, password)
                } catch (e: Exception) {
                    Timber.wtf("!!!! userId error: User was added to Authentication but not to Database --> userId was fetched as null after user registered in Authentication")
                    emit(Resource.Error<String>("An unexpected error occurred"))
                    return@flow
                }

                userId = authenticator.getUserId()
                if (userId == null) {
                    Timber.wtf("!!!! User was added to Authentication but not to Database --> userId was fetched as null after user registered in Authentication")
                    emit(Resource.Error<String>("An unexpected error occurred"))
                    return@flow
                }
            }

            /**
             * Adding the user to database.
             * @exception FirebaseFirestoreException
             * @exception Exception
             */
            try {
                repository.registerUser(userId, email, username) // TODO - fix this dangerous !!
                Timber.i("SUCCESS Database (Firestore): User added to Firestore database")
            } catch (e: FirebaseFirestoreException) {
                authenticator.deleteUserAccount()
                Timber.e("Database (Firestore): Failed to add user to Firestore: An unexpected FIRESTORE error:: deleting user from Firebase Authentication --> $e")
                // TODO - delete user from firebase database using firebase authentication (try catch it as well) (probably only have one catch with difference if statements for this one
                emit(Resource.Error<String>("An unexpected error occurred"))
            } catch (e: Exception) {
                authenticator.deleteUserAccount()
                Timber.e("Database (Firestore): Failed to add user to Firestore: An unexpected error occurred:: deleting user from Firebase Authentication  --> $e")
                emit(Resource.Error<String>("An unexpected error occurred"))
            }

            /**
             * Adding the user to database.
             * @exception FirebaseFirestoreException
             * @exception Exception
             */
            try {
                authenticator.updateUserUsername(username)
            } catch (e: Exception) {
                Timber.e("Firebase Authentication: Failed to add user to Firestore: An unexpected error occurred:: deleting user from Firebase Authentication  --> $e")
                // TODO - delete user from firebase database using firebase authentication (try catch it as well)
                // TODO - delete user from firebase firestore (try catch it as well)
                emit(Resource.Success<String>(message = "Registered Successfully")) // Yes, emit success because this isn't necessary
            }

            emit(Resource.Success<String>(message = "Registered Successfully"))
        }.flowOn(Dispatchers.IO)
}
