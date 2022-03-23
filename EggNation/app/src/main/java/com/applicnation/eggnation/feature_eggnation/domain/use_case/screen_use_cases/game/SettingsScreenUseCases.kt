package com.applicnation.eggnation.feature_eggnation.domain.use_case.screen_use_cases.game

import com.applicnation.eggnation.feature_eggnation.domain.use_case.individual_use_cases.user_use_case.get_user_data_use_case.GetUserEmailUC
import com.applicnation.eggnation.feature_eggnation.domain.use_case.individual_use_cases.user_use_case.get_user_data_use_case.GetUserEmailVerificationStatusUC
import com.applicnation.eggnation.feature_eggnation.domain.use_case.individual_use_cases.user_use_case.user_account_use_case.DeleteUserUC
import com.applicnation.eggnation.feature_eggnation.domain.use_case.individual_use_cases.user_use_case.user_account_use_case.ReloadUserUC
import com.applicnation.eggnation.feature_eggnation.domain.use_case.individual_use_cases.user_use_case.user_account_use_case.SendUserVerificationEmailUC
import com.applicnation.eggnation.feature_eggnation.domain.use_case.individual_use_cases.user_use_case.user_account_use_case.SignOutUserUC

data class SettingsScreenUseCases(
    val reloadUserUC: ReloadUserUC,
    val getUserEmailUC: GetUserEmailUC,
    val getUserEmailVerificationStatusUC: GetUserEmailVerificationStatusUC,
    val signOutUserUC: SignOutUserUC,
    val deleteUserUC: DeleteUserUC,
    val sendUserVerificationEmailUC: SendUserVerificationEmailUC
)