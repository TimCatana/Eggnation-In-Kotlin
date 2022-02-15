package com.applicnation.eggnation.feature_eggnation.data.remote.firebase

import android.net.Uri
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.userProfileChangeRequest
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import java.lang.Exception

class Authentication {
    private val auth = FirebaseAuth.getInstance()

    /**
     * Creates a new account for the user.
     * Adds the user to firebase auth console
     * @param email The user email
     * @param password The user password password must be valid according to my requirements (must be greater than
     *                 8 chars, contain caps, lowercase and numbers with no white space)
     * @exception FirebaseAuthInvalidCredentialsException The email address is badly formatted (password should always be valid)
     * @exception FirebaseAuthUserCollisionException The user already exists (the email is already in use)
     * @exception FirebaseAuthWeakPasswordException The use password is less that 6 chars. SHOULD NEVER BE THROWN!!!
     * @exception Exception All exceptions thrown from this catch block are UNEXPECTED
     */
    suspend fun signUpUser(email: String, password: String) {
        try {
            auth.createUserWithEmailAndPassword(email, password).await()
            Timber.i("SUCCESS: User signed up (registered)")
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            // TODO - need to propogate some error to the UI
            Timber.e("Failed to sign user up (register): Email is invalid  (not formatted correctly) --> $e")
        } catch (e: FirebaseAuthUserCollisionException) {
            Timber.e("Failed to sign user up (register): User already exists: --> $e ")
        } catch (e: FirebaseAuthWeakPasswordException) {
            // TODO - this exception should never be thrown
            Timber.wtf("Failed to sign user up (register): Weak Password (needs to be greater than 6 characters) --> $e")
        } catch (e: Exception) {
            Timber.wtf("Failed to sign user up (register): An unexpected error occurred --> $e")
        }
    }

    /**
     * Signs the user into their account given their email and password
     * @param email The user email
     * @param password The user password
     * @exception FirebaseAuthInvalidCredentialsException The email address is badly formatted (password should always be valid)
     * @exception FirebaseAuthInvalidUserException The user does not exist in Firebase Authentication (the user was either deleted or did not sign up yet)
     * @exception Exception All exceptions thrown from this catch block are UNEXPECTED
     */
    suspend fun signInUser(email: String, password: String) {
        try {
            auth.signInWithEmailAndPassword(email, password).await()
            Timber.i("SUCCESS: User signed in")
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            // TODO - need to propogate some error to the UI
            Timber.e("Failed to sign user in: Email is invalid (not formatted correctly) --> $e")
        } catch (e: FirebaseAuthInvalidUserException) {
            // TODO - need to propogate some error to the UI
            Timber.e("Failed to sign user in: User does not exist --> $e")
        } catch (e: Exception) {
            Timber.wtf("Failed to sign user in: An unexpected error occured --> $e")
        }
    }

    /**
     * Signs the user out of their account on their given device
     * @exception Exception All exceptions thrown from this catch block are UNEXPECTED
     */
    fun signOutUser() {
        try {
            auth.signOut()
            Timber.i("SUCCESS: User signed out")
        } catch (e: Exception) {
            // TODO - need to propogate some error to the UI
            Timber.e("Failed to sign user out: Unexpected exception $e")
        }
    }

    /**
     * Deletes a user from Firebase authentication. This action is irreversable.
     * @param email The user email
     * @param password The user password
     * @exception FirebaseAuthInvalidUserException Either the account re-authentication failed (the user inputted an incorrect password)
     *                                             OR The re-authentication failed and the program attempted to delete the user (this should never happen)
     * @exception FirebaseAuthInvalidCredentialsException Either the user's credentials are incorrect
     *                                                    OR the user tried to delete someone else's account (malicous behaviour)
     * @exception Exception All exceptions thrown from this catch block are UNEXPECTED
     * @note If the user tried to delete their account while not signed in, something is horribly wrong. I need to force stop the app
     */
    suspend fun deleteUserAccount(email: String, password: String) {
        try {
            val credential = EmailAuthProvider.getCredential(email, password)

            if (auth.currentUser != null) {
                auth.currentUser?.reauthenticate(credential)?.await()
                auth.currentUser?.delete()?.await()
            } else {
                // TODO - need to stop force stop app, something is horribly wrong
                Timber.wtf("!!!! Failed to re-authenticate and delete user: HOW DID YOU MANAGE TO TRY TO DELETE YOUR ACCOUNT WHILE NOT BEING SIGNED IN? --> need to force stop now")
            }

            Timber.i("SUCCESS: User account deleted")
        } catch (e: FirebaseAuthInvalidUserException) {
            Timber.e("Failed to re-authenticate and delete user: Is the password valid? Did the re-authentication fail and thus user can not be deleted? --> $e")
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            Timber.e("Failed to re-authenticate and delete user: Are they trying to delete another user's account??? --> $e")
        } catch (e: Exception) {
            Timber.wtf("Failed to delete user account: An unexpected error occurred --> $e")
        }
    }

    /**
     * Sends the user a verification email. The email should contain a deep link to the VerifyEmailScreen.
     * If user is not logged in when they reach the VerifyEmailScreen, then do not do anything. That is a security hazard
     * @param email The email to send password reset link to
     * @exception FirebaseAuthEmailException Password reset email failed to send
     * @exception Exception All exceptions thrown from this catch block are UNEXPECTED
     * @note TODO - may need to check FirebaseAuthInvalidCredentialsException? to see if email was properly formatted
     */
    suspend fun sendPasswordResetEmail(email: String) {
        try {
            auth.sendPasswordResetEmail(email).await()
            Timber.i("SUCCESS: User password reset")
            // TODO - propogate message saying that the email was sent
        } catch (e: FirebaseAuthEmailException) {
            // TODO - need to propogate some error to the UI
            Timber.w("Failed to send password reset email: Either email is formatted badly or email does not exists --> $e")
        } catch (e: Exception) {
            // TODO - need to propogate some error to the UI
            Timber.e("Failed to send password reset email: An unexpected error occurred--> $e")
        }
    }

    /**
     * Sends a verification email to the email that the current user is using.
     * If they inputted the incorrect email when they registered, they have the option to change their email in settings.
     * @param email The email to verify
     * @exception FirebaseAuthEmailException Verification email failed to send
     * @exception Exception All exceptions thrown from this catch block are UNEXPECTED
     * @note If the user tried to verify their email while not signed in, something is horribly wrong. I need to force stop the app
     */
    suspend fun verifyUserEmail(email: String) {
        try {
            if (auth.currentUser != null) {
                // TODO - need to add deep link into email that links into VerifyEmailScreen
                auth.currentUser?.sendEmailVerification()?.await()
            } else {
                // TODO - need to stop force stop app, something is horribly wrong
                Timber.wtf("!!!! Failed to re-authenticate and delete user: HOW DID YOU MANAGE TO TRY TO VERIFY YOU EMAIL WHILE NOT BEING SIGNED IN??? --> need to force stop now")
            }

            Timber.i("SUCCESS: Verification email sent")
            // TODO - propogate message saying that the email was sent
        } catch (e: FirebaseAuthEmailException) {
            // TODO - need to propogate some error to the UI
            Timber.w("Failed to send verification email: Either email is formatted badly or email does not exists --> $e")
        } catch (e: Exception) {
            // TODO - need to propogate some error to the UI
            Timber.e("Failed to send verification email: An unexpected error occurred --> $e")
        }

        // TODO - send user verification email
    }

    /**
     * Getters for the user's profile
     * - getUserId
     * - getUserEmail
     * - getUserUsername
     * - getUserPhotoUrl
     * - getUserEmailVerificationStatus
     */

    // TODO - probably do some extensive error checking. If user is null force stop app
    // TODO - after checking for nullability, return a !! variable, cause I don't want nullables passed around
    fun getUserId(): String? {
        return auth.currentUser?.uid
    }

    fun getUserEmail(): String? {
        return auth.currentUser?.email
    }

    fun getUserUsername(): String? {
        return auth.currentUser?.displayName
    }

    fun getUserProfilePicture(): Uri? {
        return auth.currentUser?.photoUrl
    }

    fun getUserEmailVerificationStatus(): Boolean? {
        return auth.currentUser?.isEmailVerified
    }

    /**
     * Setters for the user's profile
     * - updateUserEmailAddress
     * - updateUserPassword
     * - updateUserUsername
     * - updateProfilePicture
     */

    /**
     * Updates the user's email address .
     * @param email The user email
     * @param password The user password
     * @param newEmail The new email to set for the user's profile
     * @exception FirebaseAuthInvalidUserException The user's account is either disabled, deleted (when thrown by reauthenticate())
     *                                             OR the user's account is either disabled, deleted or credentials are no longer valid (when thrown by updateEmail())
     * @exception FirebaseAuthInvalidCredentialsException Either re-authentication failed to due Invalid email/password
     *                                                    OR user is trying to access someone else's account (malicious)
     *                                                    OR the new email address is not formatted correctly and invalid
     * @exception FirebaseAuthUserCollisionException The user is trying th update their email address with an email that already exists (probably by someone else)
     * @exception FirebaseAuthRecentLoginRequiredException The user needs to have been re-authenticated recently in order for this update to occur. SHOULD NEVER BE THROWN!!!
     * @exception Exception All exceptions thrown from this catch block are UNEXPECTED
     */
    suspend fun updateUserEmailAddress(email: String, password: String, newEmail: String) {
        try {
            val credential = EmailAuthProvider.getCredential(email, password)

            if (auth.currentUser != null) {
                auth.currentUser?.reauthenticate(credential)
                    ?.await() // TODO - if this throws a FirebaseAuthInvalidUserException thats a WTF serious error. though that should never happen
                auth.currentUser?.updateEmail(newEmail)?.await()
            } else {
                // TODO - force stop
                Timber.wtf("!!!! Failed to update user email: HOW ARE YOU TRYING TO UPDATE EMAIL WITHOUT BEING SIGNED IN??? --> need to force stop now")
            }

            Timber.i("SUCCESS: User email address updated")
            // TODO - propogate UI message saying that emaul was updated sucessfully (probably send back a boolean value>

        } catch (e: FirebaseAuthInvalidUserException) {
            // TODO - propogate UI message saying the email was not updated
            Timber.e("Failed to update user email: The user's account is either disabled, deleted or credentials are no longer valid (thrown either by reauthenticate() or updateEmail() --> $e")
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            // TODO - propogate UI message saying the email was not updated
            Timber.e("Failed to update user email: Either re-authentication failed to due Invalid email/password OR user is trying to access someone else's account OR the new email address is not formatted correctly and invalid --> $e")
        } catch (e: FirebaseAuthUserCollisionException) {
            // TODO - propogate UI message saying the email was not updated
            Timber.e("Failed to update user email: An account already exists with that email address --> $e")
        } catch (e: FirebaseAuthRecentLoginRequiredException) {
            // TODO - propogate UI message saying the email was not updated
            Timber.wtf("!!!! Failed to update user email: The re-authentication failed but the program still tried to update the user's email. An exception should have been thrown from reauthenticate() and prevented all code following it from running --> $e")
        } catch (e: Exception) {
            // TODO - propogate UI message saying the email was not updated
            Timber.e("Failed to update user email address: An unexpected error occurred --> $e")
        }
    }

    /**
     * Updates the user's password.
     * @param email The user email
     * @param password The user password
     * @param newPassword The new password to set for the user's profile
     * @exception FirebaseAuthInvalidUserException The user's account is either disabled, deleted (when thrown by reauthenticate())
     *                                             OR the user's account is either disabled, deleted or credentials are no longer valid (when thrown by updatePassword())
     * @exception FirebaseAuthInvalidCredentialsException Either re-authentication failed to due Invalid email/password
     *                                                    OR user is trying to access someone else's account (malicious)
     *                                                    OR the new password is not formatted correctly and invalid (should never occur)
     * @exception FirebaseAuthWeakPasswordException The use password is less that 6 chars. SHOULD NEVER BE THROWN!!!
     * @exception FirebaseAuthRecentLoginRequiredException The user needs to have been re-authenticated recently in order for this update to occur. SHOULD NEVER BE THROWN!!!
     * @exception Exception All exceptions thrown from this catch block are UNEXPECTED
     */
    suspend fun updateUserPassword(email: String, password: String, newPassword: String) {
        try {
            val credential = EmailAuthProvider.getCredential(email, password)

            if (auth.currentUser != null) {
                auth.currentUser?.reauthenticate(credential)
                    ?.await() // TODO - if this throws a FirebaseAuthInvalidUserException thats a WTF serious error. though that should never happen
                auth.currentUser?.updatePassword(newPassword)
            } else {
                // TODO - force stop
                Timber.wtf("!!!! Failed to update user password: HOW ARE YOU TRYING TO UPDATE PASSWORD WITHOUT BEING SIGNED IN??? --> need to force stop now")
            }

            Timber.i("SUCCESS: User password updated")
            // TODO - propogate UI message saying that password was updated sucessfully (probably send back a boolean value>
        } catch (e: FirebaseAuthInvalidUserException) {
            // TODO - propogate UI message saying the password was not updated
            Timber.e("Failed to update user password: Either user's account is disabled or deleted and re-authentication failed OR user's account is disabled, deleted or credentials are no longer valid --> $e")
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            // TODO - propogate UI message saying the password was not updated
            Timber.e("Failed to update user password: Either re-authentication failed to due Invalid email/password or if user is trying to access someone else's account OR the new password address is not formatted correctly and invalid --> $e")
        } catch (e: FirebaseAuthWeakPasswordException) {
            // TODO - propogate UI message saying the password was not updated
            Timber.wtf("Failed to update user password: Weak Password (needs to be greater than 6 characters) --> $e")
        } catch (e: FirebaseAuthRecentLoginRequiredException) {
            // TODO - propogate UI message saying the password was not updated
            Timber.wtf("!!!! Failed to update user password: The re-authentication failed but the program still tried to update the user's email. An exception should have been thrown from reauthenticate() and prevented all code following it from running --> $e")
        } catch (e: Exception) {
            // TODO - propogate UI message saying the password was not updated
            Timber.e("Failed to update user password: An unexpected error occurred --> $e")
        }
    }

    /**
     * Updates the user's username.
     * @param newUsername The new username to set for the user's profile
     * @exception FirebaseAuthInvalidUserException The user's account is either disabled, deleted or credentials are no longer valid
     * @exception Exception All exceptions thrown from this catch block are UNEXPECTED
     */
    suspend fun updateUserUsername(newUsername: String) {
        // TODO - pobably try to keep all usernames unique? Maybe create a 'usernames' collection in firestore and just try to fetch that.
        // TODO if its null good, if not don't update. This action should be run very few times, so firestore shouldn't be a money problem
        try {
            val profileUpdates = userProfileChangeRequest { displayName = newUsername }

            if (auth.currentUser != null) {
                auth.currentUser?.updateProfile(profileUpdates)?.await()
            } else {
                // TODO - force stop
                Timber.wtf("!!!! Failed to update user username: HOW ARE YOU TRUING TO UPDATE USERNAME WITHOUT BEING SIGNED IN??? --> need to force stop now")
            }

            Timber.i("SUCCESS: User username updated")
            // TODO - propogate UI message saying that username was updated sucessfully (probably send back a boolean value>
        } catch (e: FirebaseAuthInvalidUserException) {
            // TODO - propogate UI message saying the username was not updated
            // TODO - maybe if the user changes their email this may be thrown? maybe I'll need to re-authenticate then
            Timber.e("Failed to update user username: The user's account is either disabled, deleted or the credentials are now invalid --> $e")
        } catch (e: Exception) {
            // TODO - propogate UI message saying the username was not updated
            Timber.e("Failed to update user username: An unexpected error occurred --> $e")
        }
    }

    /**
     * Updates the user's profile picture.
     * @param newProfilePictureUri The new profile picture's uri to set for the user's profile
     * @exception FirebaseAuthInvalidUserException The user's account is either disabled, deleted or credentials are no longer valid
     * @exception Exception All exceptions thrown from this catch block are UNEXPECTED
     */
    suspend fun updateProfilePicture(newProfilePictureUri: Uri) {
        try {
            val profileUpdates = userProfileChangeRequest { photoUri = newProfilePictureUri }

            if (auth.currentUser != null) {
                auth.currentUser?.updateProfile(profileUpdates)?.await()
            } else {
                Timber.wtf("!!!! Failed to update user profile picture: HOW ARE YOU TRYING TO UPDATE PROFILE PICTURE WITHOUT BEING SIGNED IN??? --> need to force stop now")
            }

            Timber.i("SUCCESS: User profile picture updated")
            // TODO - propogate UI message saying that profile picture was updated sucessfully (probably send back a boolean value>
        } catch (e: FirebaseAuthInvalidUserException) {
            // TODO - propogate UI message saying the profile picture was not updated
            // TODO - maybe if the user changes their email this may be thrown? maybe I'll need to re-authenticate then
            Timber.e("Failed to update user profile picture: The user's account is either disabled, deleted or the credentials are now invalid --> $e")
        } catch (e: Exception) {
            // TODO - propogate UI message saying the profile picture was not updated
            Timber.e("Failed to update user profile picture: An unexpected error occurred --> $e")
        }
    }

}
