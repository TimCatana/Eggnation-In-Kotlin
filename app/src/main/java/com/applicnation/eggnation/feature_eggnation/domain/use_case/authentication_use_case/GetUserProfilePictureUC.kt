package com.applicnation.eggnation.feature_eggnation.domain.use_case.authentication_use_case

import android.net.Uri
import com.applicnation.eggnation.feature_eggnation.domain.repository.AuthenticationRepository
import javax.inject.Inject

class GetUserProfilePictureUC @Inject constructor(
    private val authenticator: AuthenticationRepository
) {
    /**
     * Get's the user's profile picture Uri from Firebase Authentication
     * @note authenticator.getUserProfilePicture() will never return null because the current user logged in status is checked beforehand
     */
    operator fun invoke(): Uri {
        if (authenticator.getUserLoggedInStatus() == null) {
           throw Exception()
            // TODO - throw exception? I ned t
        } else {
            return authenticator.getUserProfilePicture()!!
        }
    }
}
