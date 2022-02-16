package com.applicnation.eggnation.feature_eggnation.domain.use_case.authentication_use_case

import com.applicnation.eggnation.feature_eggnation.domain.repository.AuthenticationRepository
import javax.inject.Inject

class SendUserVerificationEmailUC @Inject constructor(
    private val authenticator: AuthenticationRepository
){
    suspend operator fun invoke() {
        // TODO - maybe add a try catch here? to propogate the error
        authenticator.sendVerificationEmail()
    }
}