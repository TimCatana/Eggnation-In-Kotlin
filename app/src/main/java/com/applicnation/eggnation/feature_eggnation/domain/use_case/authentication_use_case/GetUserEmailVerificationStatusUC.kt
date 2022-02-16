package com.applicnation.eggnation.feature_eggnation.domain.use_case.authentication_use_case

import com.applicnation.eggnation.feature_eggnation.domain.repository.AuthenticationRepository
import javax.inject.Inject

class GetUserEmailVerificationStatusUC @Inject constructor(
    private val authenticator: AuthenticationRepository
) {
    operator fun invoke(): Boolean? {
        // TODO - maybe add a try catch here? to propogate the error
        return authenticator.getUserEmailVerificationStatus()
    }
}
