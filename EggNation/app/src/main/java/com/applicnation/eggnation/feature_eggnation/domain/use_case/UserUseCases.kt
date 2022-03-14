package com.applicnation.eggnation.feature_eggnation.domain.use_case

import com.applicnation.eggnation.feature_eggnation.domain.use_case.user_use_case.get_user_data_use_case.*
import com.applicnation.eggnation.feature_eggnation.domain.use_case.user_use_case.update_user_data_use_case.UpdateUserEmailAddressUC
import com.applicnation.eggnation.feature_eggnation.domain.use_case.user_use_case.update_user_data_use_case.UpdateUserPasswordUC
import com.applicnation.eggnation.feature_eggnation.domain.use_case.user_use_case.user_account_use_case.*

data class UserUseCases(
    val signUpUserUC: SignUpUserUC,
    val signInUserUC: SignInUserUC,
    val signOutUserUC: SignOutUserUC,
    val deleteUserUC: DeleteUserUC,
    val reauthenticateUser: ReauthenticateUser,
    val reloadUserUC: ReloadUserUC,

    val sendUserPasswordResetEmailUC: SendUserPasswordResetEmailUC,
    val sendUserVerificationEmailUC: SendUserVerificationEmailUC,

    val getUserIdUC: GetUserIdUC,
    val getUserEmailUC: GetUserEmailUC,
    val getUserEmailVerificationStatusUC: GetUserEmailVerificationStatusUC,

    val updateUserEmailAddressUC: UpdateUserEmailAddressUC,
    val updateUserPasswordUC: UpdateUserPasswordUC,
)
