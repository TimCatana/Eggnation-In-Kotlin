package com.applicnation.eggnation.auth.stack

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.applicnation.eggnation.firebase.Authentication
import com.applicnation.eggnationkotlin.firebase.Firestore

    @Composable
    fun RegisterScreen(
        navController: NavController
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
//            modifier = Modifier.clickable {
//                navController.navigate(route = Screen.Detail.route)
//            },
                text = "Register",
                color = Color.Green,
                fontSize = MaterialTheme.typography.h3.fontSize,
                fontWeight = FontWeight.Bold
            )
        }
    }

class RegisterScreen {
    private val firestore: Firestore = Firestore()
    private val auth: Authentication = Authentication()

    private val mWhiteSpaceChars = "\\s".toRegex()
    private val mLowerCaseChars = "[a-z]".toRegex()
    private val mUpperCaseChars = "[A-Z]".toRegex()
    private val mNumberChars = "\\d".toRegex()

    /**
     * Checks to see if the user valid inputs
     * - Username is unique
     * - Password is strong and meets requirements
     * - Email is in a valid email pattern (i.e. <chars>@<chars>.<chars>)
     */
    private fun isValidInput(): Boolean {
        return (isValidPassword() && isUniqueUsername() && isValidEmail())
    }

    /**
     * Queries the database (IDK which one yet) to see if username exists already
     * @return (Boolean) Whether the username inputted is unique or not
     */
    private fun isUniqueUsername(): Boolean {
//        val userUserName = binding.etRegisterUsername.text.toString()
        // TODO - iterate through a list of usernames in the database and check for uniqueness
        return true
    }

    /**
     * Checks to see if email is in a valid format
     * @Note This does NOT check if the email exists and is a valid email address
     * @Note Firebase Authentication will deal with duplicate email addresses
     * @return (Boolean) Whether the email inputted is in a valid format or not
     */
    private fun isValidEmail(): Boolean {
//        val userEmail = binding.etRegisterEmail.text.toString()
        // TODO - send UI message saying to input a valid email address
//        return android.util.Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()
        return true
    }

    /**
     * Checks to see if the password the user inputted a valid, strong password
     * - Password and ConfirmPassword must match
     * - Password must not be empty
     * - Password must be at least 8 characters long
     * - Password must not contain whitespace
     * - Password must contain lowercase, uppercase and integer characters
     * @return (Boolean) Whether or not the password input is valid
     */
    private fun isValidPassword(): Boolean {
//        val userPassword = binding.etRegisterPassword.text.toString()
//        // TODO - need to add a 'confirm password' section and test against that
//
//        var isValid = true
//
//        // TODO - test the password input against the confirm password input
//
//        Log.i("RegisterFragment", "$userPassword")
//
//
//        if (userPassword.isBlank()) {
//            Log.i("RegisterFragment", "isBlank")
//            // TODO - send UI message to tell the user to enter a password
//            isValid = false
//        }
//
//        if (userPassword.length < 8) {
//            Log.i("RegisterFragment", "is less than 8 chars long")
//            // TODO - send UI message to tell the user to make password longer
//            isValid = false
//        }
//
//        if (userPassword.contains(mWhiteSpaceChars)) {
//            Log.i("RegisterFragment", "contains whitespace")
//            // TODO - send UI message to tell the user to get rid of whitespace
//            isValid = false
//        }
//
//        if (!(userPassword.contains(mLowerCaseChars)) || !(userPassword.contains(mUpperCaseChars)) || !(userPassword.contains(
//                mNumberChars
//            ))
//        ) {
//            Log.i("RegisterFragment", "does not contain lowercase uppercase and numbers")
//            // TODO - send UI message to tell the user to get include lowercase, uppercase and numbers in password
//            isValid = false
//        }
//
//        return isValid
        return true
    }

}