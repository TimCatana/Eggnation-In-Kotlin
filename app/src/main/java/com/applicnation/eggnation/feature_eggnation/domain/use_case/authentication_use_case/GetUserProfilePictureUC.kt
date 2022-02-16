package com.applicnation.eggnation.feature_eggnation.domain.use_case.authentication_use_case

import android.net.Uri
import com.applicnation.eggnation.feature_eggnation.domain.repository.AuthenticationRepository
import javax.inject.Inject

class GetUserProfilePictureUC @Inject constructor(
    private val authenticator: AuthenticationRepository
) {
    operator fun invoke(): Uri? {
        // TODO - maybe add a try catch here? to propogate the error
        return authenticator.getUserProfilePicture()
    }
}
