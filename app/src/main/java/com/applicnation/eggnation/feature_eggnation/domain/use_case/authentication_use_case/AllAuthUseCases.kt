package com.applicnation.eggnation.feature_eggnation.domain.use_case.authentication_use_case

data class AllAuthUseCases(
    val signUpUserUC: SignUpUserUC,
    val signInUserUC: SignInUserUC,
    val signOutUserUC: SignOutUserUC,
    val deleteUserAccountUC: DeleteUserAccountUC,

    val sendUserVerificationEmailUC: SendUserVerificationEmailUC,
    val sendUserPasswordResetEmailUC: SendUserPasswordResetEmailUC,

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
