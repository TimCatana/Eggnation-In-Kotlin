package com.applicnation.eggnation.feature_eggnation.data.repository

import com.applicnation.eggnation.feature_eggnation.data.remote.firebase.Functions
import com.applicnation.eggnation.feature_eggnation.domain.repository.FunctionsRepository
import javax.inject.Inject

class FunctionsRepositoryImpl @Inject constructor(
    private val functions: Functions
): FunctionsRepository {

    override suspend fun updateUserEmail(email: String) {
        functions.updateUserEmail(email)
    }
}