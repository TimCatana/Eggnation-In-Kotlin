package com.applicnation.eggnation.feature_eggnation.domain.use_case.authentication_use_case

import android.net.Uri
import com.applicnation.eggnation.feature_eggnation.domain.repository.AuthenticationRepository
import javax.inject.Inject

class UpdateUserProfilePictureUC @Inject constructor(
    private val authenticator: AuthenticationRepository
) {
    suspend operator fun invoke(newProfilePictureUri: Uri) {
        // TODO - maybe add a try catch here? to propogate potential thrown errors
        authenticator.updateUserProfilePicture(newProfilePictureUri)
    }
}
