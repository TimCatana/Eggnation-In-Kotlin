package com.applicnation.eggnation.feature_eggnation.domain.use_case.user_use_case.update_user_data_use_case

import android.net.Uri
import com.applicnation.eggnation.feature_eggnation.domain.repository.AuthenticationRepository
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject

class UpdateUserProfilePictureUC @Inject constructor(
    private val authenticator: AuthenticationRepository
) {
    /**
     * Updates the user's profile picture.
     * @param newProfilePictureUri The new profile picture's uri to set for the user's profile
     * @exception FirebaseAuthInvalidUserException
     * @exception Exception All exceptions caught in this block are UNEXPECTED
     * @note Read the log messages below to see what each exception means (it is too messy to put all the info in this comment)
     */
    suspend operator fun invoke(newProfilePictureUri: Uri) {
        if (authenticator.getUserLoggedInStatus() == null) {
            throw Exception()
            // TODO - throw exception?
            // TODO - this is a really bad situation to be in
        }

        // TODO - propogate successes and errors to UI
        try {
            authenticator.updateUserProfilePicture(newProfilePictureUri)
            Timber.i("SUCCESS: User profile picture updated")
        } catch (e: FirebaseAuthInvalidUserException) {
            // TODO - maybe if the user changes their email this may be thrown? maybe I'll need to re-authenticate then
            Timber.e("Failed to update user profile picture: The user's account is either disabled, deleted or the credentials are now invalid --> $e")
        } catch (e: Exception) {
            Timber.e("Failed to update user profile picture: An unexpected error occurred --> $e")
        }
    }
}
