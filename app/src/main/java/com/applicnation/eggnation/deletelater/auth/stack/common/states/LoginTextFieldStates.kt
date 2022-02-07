package com.applicnation.eggnation.deletelater.auth.stack.common.states

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class LoginTextFieldStates {
    var emailText by mutableStateOf("")
    var passwordText by mutableStateOf("")

    var emailError by mutableStateOf<String?>(null)
    var passwordError by mutableStateOf<String?>(null)

    fun enableLoginButton(): Boolean {
        return emailText.isNotEmpty() && emailError == null && passwordText.isNotEmpty() && passwordError == null
    }

    /**
     * Checks to see if email is in a valid format
     * @Note This does NOT check if the email exists and is a valid email address
     * @Note Firebase Authentication will deal with duplicate email addresses
     * @return Sets emailError value
     */
    fun validateEmail() {
        if (android.util.Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
            emailError = null
        } else {
            emailError = "please enter a valid email address!"
        }
    }

    fun validatePassword() {
        if (passwordText.isNotEmpty()) {
            passwordError = null
        } else {
            passwordError = "please enter a password!"
        }
    }
}
