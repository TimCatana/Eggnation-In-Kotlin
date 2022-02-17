package com.applicnation.eggnation.feature_eggnation.domain.use_case.user_use_case

import com.applicnation.eggnation.feature_eggnation.domain.use_case.user_use_case.get_user_data_use_case.*
import com.applicnation.eggnation.feature_eggnation.domain.use_case.user_use_case.update_user_data_use_case.UpdateUserEmailAddressUC
import com.applicnation.eggnation.feature_eggnation.domain.use_case.user_use_case.update_user_data_use_case.UpdateUserPasswordUC
import com.applicnation.eggnation.feature_eggnation.domain.use_case.user_use_case.update_user_data_use_case.UpdateUserProfilePictureUC
import com.applicnation.eggnation.feature_eggnation.domain.use_case.user_use_case.update_user_data_use_case.UpdateUserUsernameUC
import com.applicnation.eggnation.feature_eggnation.domain.use_case.user_use_case.user_account_use_case.*

data class UserUseCases(
    val signUpUserUC: SignUpUserUC,
    val signInUserUC: SignInUserUC,
    val signOutUserUC: SignOutUserUC,
    val deleteUserUC: DeleteUserUC,

    val sendUserPasswordResetEmailUC: SendUserPasswordResetEmailUC,
    val sendUserVerificationEmailUC: SendUserVerificationEmailUC,

    val getUserIdUC: GetUserIdUC,
    val getUserEmailUC: GetUserEmailUC,
    val getUserUsernameUC: GetUserUsernameUC,
    val getUserProfilePictureUC: GetUserProfilePictureUC,
    val getUserEmailVerificationStatusUC: GetUserEmailVerificationStatusUC,

    val updateUserEmailAddressUC: UpdateUserEmailAddressUC,
    val updateUserPasswordUC: UpdateUserPasswordUC,
    val updateUserUsernameUC: UpdateUserUsernameUC,
    val updateUserProfilePictureUC: UpdateUserProfilePictureUC
)
