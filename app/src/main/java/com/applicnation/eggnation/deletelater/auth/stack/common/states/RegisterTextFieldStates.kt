package com.applicnation.eggnation.deletelater.auth.stack.common.states

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue

class RegisterTextFieldStates() {

    var usernameText by mutableStateOf("")
    var emailText by mutableStateOf("")
    var passwordText by mutableStateOf("")
    var confirmPasswordText by mutableStateOf("")

    var usernameError by mutableStateOf<String?>(null)
    var emailError by mutableStateOf<String?>(null)
    var passwordError by mutableStateOf<String?>(null)
    var confirmPasswordError by mutableStateOf<String?>(null)


    fun enableRegisterButton(): Boolean {
        return usernameText.isNotEmpty() && usernameError == null
                && emailText.isNotEmpty() && emailError == null
                && passwordText.isNotEmpty() && passwordError == null
                && confirmPasswordText.isNotEmpty() && confirmPasswordError == null
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

    /**
     * Checks to see if the password and confirmPassword are equal
     * @return Sets confirmPassword value
     */
    fun validateConfirmPassword() {
        // TODO - test the password input against the confirm password input
        if (passwordText == confirmPasswordText) {
            confirmPasswordError = null
        } else {
            confirmPasswordError = "passwords must match!"
        }
    }

    /**
     * Checks to see if the password the user inputted a valid, strong password
     * - Password and ConfirmPassword must match
     * - Password must not be empty
     * - Password must be at least 8 characters long
     * - Password must not contain whitespace
     * - Password must contain lowercase, uppercase and integer characters
     * @return Sets passwordError value
     */
    fun validatePassword() {
        val mWhiteSpaceChars = "\\s".toRegex()
        val mLowerCaseChars = "[a-z]".toRegex()
        val mUpperCaseChars = "[A-Z]".toRegex()
        val mNumberChars = "\\d".toRegex()

        var isValid = true

        if (passwordText.isBlank()) {
            Log.i("RegisterFragment", "isBlank")
            // TODO - send UI message to tell the user to enter a password
            isValid = false
        }

        if (passwordText.length < 8) {
            Log.i("RegisterFragment", "is less than 8 chars long")
            // TODO - send UI message to tell the user to make password longer
            isValid = false
        }

        if (passwordText.contains(mWhiteSpaceChars)) {
            Log.i("RegisterFragment", "contains whitespace")
            // TODO - send UI message to tell the user to get rid of whitespace
            isValid = false
        }

        if (!(passwordText.contains(mLowerCaseChars)) || !(passwordText.contains(mUpperCaseChars)) || !(passwordText.contains(
                mNumberChars
            ))
        ) {
            Log.i("RegisterFragment", "does not contain lowercase uppercase and numbers")
            // TODO - send UI message to tell the user to get include lowercase, uppercase and numbers in password
            isValid = false
        }

        if (isValid) {
            passwordError = null
        } else {
            passwordError = "multiple/npassword/nerrors"
        }
    }

    /**
     * Checks to make sure that the username is unique
     * @return Sets usernameError value
     */
    fun validateUsername() {
        // TODO - check if the username is unique or not
        usernameError = null
    }
}