package com.applicnation.eggnation.feature_eggnation.domain.util

class FailedToSignUpException(message: String): Exception(message)
class FailedToSignInException(message: String): Exception(message)
class FailedToSignOutException(message: String): Exception(message)

class FailedToReauthenticateException(message: String): Exception(message)
class FailedToDeleteAccountException(message: String): Exception(message)

class FailedToSendPasswordResetEmailException(message: String): Exception(message)