package com.applicnation.eggnation.auth.stack

class EmailState : TextFieldState(
    validator = { email -> isEmailValid(email) },
    errorMessage = { email -> emailErrorMessage(email) }
)

private fun isEmailValid(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

private fun emailErrorMessage(email: String): String {
    return "Email $email is invalid"
}