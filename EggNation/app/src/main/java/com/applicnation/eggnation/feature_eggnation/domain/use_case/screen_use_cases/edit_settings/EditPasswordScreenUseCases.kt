package com.applicnation.eggnation.feature_eggnation.domain.use_case.screen_use_cases.edit_settings

import com.applicnation.eggnation.feature_eggnation.domain.use_case.individual_use_cases.user_use_case.update_user_data_use_case.UpdateUserPasswordUC

data class EditPasswordScreenUseCases(
    val updateUserPasswordUC: UpdateUserPasswordUC
)