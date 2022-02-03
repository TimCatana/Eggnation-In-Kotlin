package com.applicnation.eggnation.auth.stack

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.applicnation.eggnation.firebase.Authentication
import com.applicnation.eggnation.navigation.AuthScreen
import com.applicnation.eggnation.navigation.PolicyScreen
import com.applicnation.eggnation.ui.theme.EggNationTheme
import com.applicnation.eggnationkotlin.firebase.Firestore
import kotlinx.coroutines.CoroutineScope


// TODO - set all text to string resources to internationalize later


private enum class RegisterConstrainsIds {
    TXT_TITLE,
    TF_USERNAME,
    TF_EMAIL,
    TF_PASSWORD,
    TF_CONFIRM_PASSWORD,
    ROW_ACCOUNT_INFO,
    ROW_NOTICE,
    ROW_POLICIES,
    BTN_REGISTER
}


@Composable
fun RegisterScreen(
    navController: NavController
) {

    var emailState =
        remember { EmailState() } // EmailState extends TextFieldState and already has mutableStateOf
    val localFocusManager = LocalFocusManager.current
    localFocusManager.moveFocus((FocusDirection.Down))


    // TODO - need to do some extreme refactoring
    val auth: Authentication = Authentication()
    val rsFunctions: RegisterScreenFunctions = RegisterScreenFunctions()

    var userNameTextState by remember { mutableStateOf(TextFieldValue("")) }
    var emailTextState by remember { mutableStateOf(TextFieldValue("")) }
    var passwordTextState by remember { mutableStateOf(TextFieldValue("")) }
    var confirmPasswordTextState by remember { mutableStateOf(TextFieldValue("")) }
    var showPasswordState by remember { mutableStateOf(false) }

    val registerScope = rememberCoroutineScope()

    BoxWithConstraints() {
        val constraints = registrerPhoneContraints()

        ConstraintLayout(constraints, modifier = Modifier.fillMaxSize()) {
            Text(
                modifier = Modifier.layoutId(RegisterConstrainsIds.TXT_TITLE),
                text = "Register",
                color = Color.Green,
                fontSize = MaterialTheme.typography.h3.fontSize,
                fontWeight = FontWeight.Bold
            )
            OutlinedTextField(
                modifier = Modifier.layoutId(RegisterConstrainsIds.TF_USERNAME),
                value = userNameTextState,
                label = { Text(text = "username") },
                singleLine = true,
//                keyBoardOptions = KeyboardOptions(
//                    keyboardType = KeyboardType.Text,
//                    imeAction = ImeAction.Next
//                ),
//                keyboardActions = KeyboardActions(
//                    onNext = {focusManager.moveFocus(FocusDirection.Down)}
//                ),
                onValueChange = { userNameTextState = it }
            )
            EmailTF(
                email = emailState.text,
                error = emailState.error,
                onEmailChanged = {
                    emailState.text = it
                    emailState.validate()
                },
                onImeAction = {
                    localFocusManager.moveFocus((FocusDirection.Down))
                }
            )

            /**
             * for register button.
             * Button(enabled = emailState.isValid() && passwordState.isValid() ...)
             */
            OutlinedTextField(
                modifier = Modifier.layoutId(RegisterConstrainsIds.TF_PASSWORD),
                value = passwordTextState,
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
//                keyboardActions = KeyboardActions(
//                    onNext = {focusManager.moveFocus(FocusDirection.Down)}
//                ),
                isError = true, // TODO - set these is inputs are invalid
                visualTransformation = if (showPasswordState) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
                trailingIcon = {
                    if (showPasswordState) {
                        IconButton(onClick = { showPasswordState = false }) {
                            Icon(
                                imageVector = Icons.Filled.Visibility,
                                contentDescription = "hide password"
                            )
                        }
                    } else {
                        IconButton(onClick = { showPasswordState = true }) {
                            Icon(
                                imageVector = Icons.Filled.VisibilityOff,
                                contentDescription = "show password"
                            )
                        }
                    }
                },
                label = { Text(text = "password") },
                onValueChange = { passwordTextState = it }
            )
            OutlinedTextField(
                modifier = Modifier.layoutId(RegisterConstrainsIds.TF_CONFIRM_PASSWORD),
                value = confirmPasswordTextState,
                singleLine = true,
//                keyBoardOptions = KeyboardOptions(
//                    imeAction = ImeAction.Done
//                ),
//                keyboardActions = KeyboardActions(
//                    onDone = {focusManager.moveFocus(FocusDirection.Down)}
//                ),
                visualTransformation = PasswordVisualTransformation(),
                label = { Text(text = "confirm password") },
                onValueChange = { confirmPasswordTextState = it }
            )
            Row(modifier = Modifier.layoutId(RegisterConstrainsIds.ROW_ACCOUNT_INFO)) {
                Text(
                    text = "Already have an account? ",
                    color = Color.Gray,
                    fontSize = MaterialTheme.typography.body1.fontSize,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    // TODO - pop this off the stack when going pack to login screen
                    modifier = Modifier.clickable {
                        navController.popBackStack()
                    },
                    text = "Login here",
                    color = Color.Gray,
                    fontSize = MaterialTheme.typography.body1.fontSize,
                    fontWeight = FontWeight.Bold
                )
            }
            Button(
                modifier = Modifier.layoutId(RegisterConstrainsIds.BTN_REGISTER),
                onClick = {
                    if (rsFunctions.isValidInput(
                            passwordTextState.text.toString(),
                            emailTextState.text.toString(),
                            userNameTextState.text.toString()
                        )
                    ) {
                        auth.createUserAccount(
                            registerScope,
                            emailTextState.text.toString(),
                            passwordTextState.text.toString()
                        )
                    } else {
                        Log.i("RegisterScreen", "Failed to create account")
                    }

                    auth.createUserAccount(
                        registerScope,
                        emailTextState.toString(),
                        passwordTextState.toString()
                    )
                })
            {
                Text(text = "Register")
            }
            Row(modifier = Modifier.layoutId(RegisterConstrainsIds.ROW_NOTICE)) {
                Text(
                    text = "By registering you agree to our ",
                    color = Color.Gray,
                    fontSize = MaterialTheme.typography.body1.fontSize,
                    fontWeight = FontWeight.Bold
                )
            }
            Row(modifier = Modifier.layoutId(RegisterConstrainsIds.ROW_POLICIES)) {
                Text(
                    modifier = Modifier.clickable {
                        navController.navigate(route = PolicyScreen.TermsOfService.route)
                    },
                    text = "Terms of service ",
                    color = Color.Gray,
                    fontSize = MaterialTheme.typography.body1.fontSize,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "and ",
                    color = Color.Gray,
                    fontSize = MaterialTheme.typography.body1.fontSize,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    modifier = Modifier.clickable {
                        navController.navigate(route = PolicyScreen.PrivacyPolocy.route)
                    },
                    text = "Privacy Policy",
                    color = Color.Gray,
                    fontSize = MaterialTheme.typography.body1.fontSize,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
}


@Composable
fun EmailTF(
    email: String,
    error: String?,
    onEmailChanged: (String) -> Unit,
    onImeAction: () -> Unit
) {

    Column {
        OutlinedTextField(
            modifier = Modifier.layoutId(RegisterConstrainsIds.TF_EMAIL),
            value = email,
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            label = { Text(text = "email") },
//                keyboardActions = KeyboardActions(
//                    onNext = {focusManager.moveFocus(FocusDirection.Down)}
//                ),
//                keyBoardOptions = KeyboardOptions(
//                    imeAction = ImeAction.Next
//                )
            onValueChange = {
                onEmailChanged(it)
            },
            isError = error != null
        )

        error?.let { ErrorField(it) }

    }
}

@Composable
fun ErrorField(error: String) {
    Text(
        text = error,
        modifier = Modifier.fillMaxWidth(),
        style = TextStyle(color = MaterialTheme.colors.error)
    )
}


private fun registrerPhoneContraints(): ConstraintSet {
    return ConstraintSet {
        val topGuideline = createGuidelineFromTop(0.24f)
        val txtTitle = createRefFor(RegisterConstrainsIds.TXT_TITLE)
        val tfUsername = createRefFor(RegisterConstrainsIds.TF_USERNAME)
        val tfEmail = createRefFor(RegisterConstrainsIds.TF_EMAIL)
        val tfPassword = createRefFor(RegisterConstrainsIds.TF_PASSWORD)
        val tfConfirmPassword = createRefFor(RegisterConstrainsIds.TF_CONFIRM_PASSWORD)
        val rowAccountInfo = createRefFor(RegisterConstrainsIds.ROW_ACCOUNT_INFO)
        val btnRegister = createRefFor(RegisterConstrainsIds.BTN_REGISTER)
        val rowLegalInfo = createRefFor(RegisterConstrainsIds.ROW_NOTICE)
        val rowPolicies = createRefFor(RegisterConstrainsIds.ROW_POLICIES)


        constrain(txtTitle) {
            top.linkTo(anchor = topGuideline)
            start.linkTo(anchor = parent.start)
            end.linkTo(anchor = parent.end)
        }
        constrain(tfUsername) {
            top.linkTo(anchor = txtTitle.bottom, margin = 16.dp)
            start.linkTo(anchor = parent.start)
            end.linkTo(anchor = parent.end)
        }
        constrain(tfEmail) {
            top.linkTo(anchor = tfUsername.bottom, margin = 16.dp)
            start.linkTo(anchor = parent.start)
            end.linkTo(anchor = parent.end)
        }
        constrain(tfPassword) {
            top.linkTo(anchor = tfEmail.bottom, margin = 16.dp)
            start.linkTo(anchor = parent.start)
            end.linkTo(anchor = parent.end)
        }
        constrain(tfConfirmPassword) {
            top.linkTo(anchor = tfPassword.bottom, margin = 16.dp)
            start.linkTo(anchor = parent.start)
            end.linkTo(anchor = parent.end)
        }
        constrain(rowAccountInfo) {
            top.linkTo(anchor = tfConfirmPassword.bottom, margin = 8.dp)
            start.linkTo(anchor = tfConfirmPassword.start)
            end.linkTo(anchor = tfConfirmPassword.end)
        }
        constrain(btnRegister) {
            top.linkTo(anchor = rowAccountInfo.bottom, margin = 16.dp)
            start.linkTo(anchor = parent.start)
            end.linkTo(anchor = parent.end)
        }
        constrain(rowLegalInfo) {
            bottom.linkTo(anchor = rowPolicies.top)
            start.linkTo(anchor = tfPassword.start)
            end.linkTo(anchor = tfPassword.end)
        }
        constrain(rowPolicies) {
            bottom.linkTo(anchor = parent.bottom)
            start.linkTo(anchor = tfPassword.start)
            end.linkTo(anchor = tfPassword.end)
        }
    }
}

//private fun tabletContraints(): ConstraintSet {}


@Preview(showBackground = true)
@Composable
private fun registerPreview() {
    EggNationTheme {
        RegisterScreen(rememberNavController())
    }
}


class RegisterScreenFunctions {
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
    fun isValidInput(
        userPassword: String,
        userEmail: String,
        userUserName: String
    ): Boolean {
        return (isValidPassword(userPassword) && isUniqueUsername(userUserName) && isValidEmail(
            userEmail
        ))
    }

    /**
     * Queries the database (IDK which one yet) to see if username exists already
     * @return (Boolean) Whether the username inputted is unique or not
     */
    private fun isUniqueUsername(userUserName: String): Boolean {
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
    private fun isValidEmail(userEmail: String): Boolean {
        // TODO - send UI message saying to input a valid email address
        return android.util.Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()
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
    private fun isValidPassword(userPassword: String): Boolean {
        // TODO - need to add a 'confirm password' section and test against that

        var isValid = true

        // TODO - test the password input against the confirm password input

        Log.i("RegisterFragment", "$userPassword")


        if (userPassword.isBlank()) {
            Log.i("RegisterFragment", "isBlank")
            // TODO - send UI message to tell the user to enter a password
            isValid = false
        }

        if (userPassword.length < 8) {
            Log.i("RegisterFragment", "is less than 8 chars long")
            // TODO - send UI message to tell the user to make password longer
            isValid = false
        }

        if (userPassword.contains(mWhiteSpaceChars)) {
            Log.i("RegisterFragment", "contains whitespace")
            // TODO - send UI message to tell the user to get rid of whitespace
            isValid = false
        }

        if (!(userPassword.contains(mLowerCaseChars)) || !(userPassword.contains(mUpperCaseChars)) || !(userPassword.contains(
                mNumberChars
            ))
        ) {
            Log.i("RegisterFragment", "does not contain lowercase uppercase and numbers")
            // TODO - send UI message to tell the user to get include lowercase, uppercase and numbers in password
            isValid = false
        }

        return isValid
        return true
    }

}