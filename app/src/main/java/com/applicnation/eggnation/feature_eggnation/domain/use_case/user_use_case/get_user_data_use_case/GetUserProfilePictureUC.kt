package com.applicnation.eggnation.feature_eggnation.domain.use_case.user_use_case.get_user_data_use_case

import android.net.Uri
import com.applicnation.eggnation.R
import com.applicnation.eggnation.feature_eggnation.domain.repository.AuthenticationRepository
import timber.log.Timber
import javax.inject.Inject

class GetUserProfilePictureUC @Inject constructor(
    private val authenticator: AuthenticationRepository
) {
    /**
     * Get's the user's profile picture Uri from Firebase Authentication
     * @note authenticator.getUserProfilePicture() will never return null because the current user logged in status is checked beforehand
     */
    operator fun invoke(): Uri {
        val profilePicture = authenticator.getUserProfilePicture()

        if (profilePicture == null) {
            // TODO - this is a very bad case... need to do something
            Timber.wtf("!!! User is signed out while trying to go to settings screen")
            return Uri.parse("https://images.unsplash.com/photo-1553095066-5014bc7b7f2d?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MXx8d2FsbCUyMGJhY2tncm91bmR8ZW58MHx8MHx8&w=1000&q=80")
        } else {
            return profilePicture
        }
    }
}
