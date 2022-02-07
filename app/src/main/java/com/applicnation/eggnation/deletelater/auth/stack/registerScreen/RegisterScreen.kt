//package com.applicnation.eggnation.auth.stack
//
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.text.KeyboardActions
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.material.*
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Visibility
//import androidx.compose.material.icons.filled.VisibilityOff
//import androidx.compose.runtime.*
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.focus.FocusDirection
//import androidx.compose.ui.focus.FocusManager
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.layout.layoutId
//import androidx.compose.ui.platform.LocalFocusManager
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.input.*
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.constraintlayout.compose.ConstraintLayout
//import androidx.constraintlayout.compose.ConstraintSet
//import androidx.navigation.NavController
//import androidx.navigation.compose.rememberNavController
//import com.applicnation.eggnation.composables.ErrorField
//import com.applicnation.eggnation.feature_eggnation.data.firebase.Authentication
//import com.applicnation.eggnation.feature_eggnation.presentation.navigation.PolicyScreen
//import com.applicnation.eggnation.deletelater.auth.stack.common.composables.HeaderText
//import com.applicnation.eggnation.deletelater.auth.stack.common.states.RegisterTextFieldStates
//import com.applicnation.eggnation.ui.theme.EggNationTheme
//import com.applicnation.eggnation.feature_eggnation.data.remote.firebase_firestore.Firestore
//
//
//// TODO - set all text to string resources to internationalize later
//
//private enum class RegisterConstrainsIds {
//    TXT_TITLE,
//    TF_USERNAME,
//    TF_EMAIL,
//    TF_PASSWORD,
//    TF_CONFIRM_PASSWORD,
//    ROW_ACCOUNT_INFO,
//    ROW_NOTICE,
//    ROW_POLICIES,
//    BTN_REGISTER
//}
//
//
//@Composable
//fun RegisterScreen(
//    navController: NavController
//) {
////    var emailState = remember { EmailState() }
////    var passwordState = remember { PasswordState() }
//
//
//    var state = remember { RegisterTextFieldStates() }
//
//    val localFocusManager = LocalFocusManager.current
//
//    // TODO - need to do some extreme refactoring
//    val auth: Authentication = Authentication()
//    val rsFunctions: RegisterScreenFunctions = RegisterScreenFunctions()
//
//    var userNameTextState by remember { mutableStateOf(TextFieldValue("")) }
//    var confirmPasswordTextState by remember { mutableStateOf(TextFieldValue("")) }
//
//    val registerScope = rememberCoroutineScope()
//
//    BoxWithConstraints() {
//        val constraints = registrerPhoneContraints()
//
//        ConstraintLayout(constraints, modifier = Modifier.fillMaxSize()) {
//            HeaderText(
//                text = "Register", // TODO - set a string resource
//                constraintId = RegisterConstrainsIds.TXT_TITLE
//            )
//            RegisterUsernameTF(
//                usernameValue = state.usernameText,
//                onUsernameChanged = {
//                    state.usernameText = it
//                    state.validateUsername()
//                },
//                error = state.usernameError,
//                focusManager = localFocusManager,
//                constraintId = RegisterConstrainsIds.TF_USERNAME
//            )
//            RegisterEmailTF(
//                emailValue = state.emailText,
//                onEmailChanged = {
//                    state.emailText = it
//                    state.validateEmail()
//                },
//                constraintId = RegisterConstrainsIds.TF_EMAIL,
//                error = state.emailError,
//                focusManager = localFocusManager
//            )
//            RegisterPasswordTF(
//                constraintId = RegisterConstrainsIds.TF_PASSWORD,
//                passwordValue = state.passwordText,
//                onPasswordChanged = {
//                    state.passwordText = it
//                    state.validatePassword()
//                    state.validateConfirmPassword()
//                },
//                error = state.passwordError,
//                focusManager = localFocusManager,
//            )
//            RegisterConfirmPasswordTF(
//                confirmPasswordValue = state.confirmPasswordText,
//                onConfirmPasswordChanged = {
//                    state.confirmPasswordText = it
//                    state.validateConfirmPassword()
//                },
//                constraintId = RegisterConstrainsIds.TF_CONFIRM_PASSWORD,
//                error = state.confirmPasswordError,
//                focusManager = localFocusManager
//            )
//
//            /**
//             * for register button.
//             * Button(enabled = emailState.isValid() && passwordState.isValid() ...)
//             */
//            AccountInfoText(
//                accountQuestion = "Already Have An Account? ",
//                actionQuestion = "Login Here",
//                modifier = Modifier.layoutId(RegisterConstrainsIds.ROW_ACCOUNT_INFO),
//                navigateModifier = Modifier.clickable {
//                    navController.popBackStack()
//                },
//            )
//            Button(
//                modifier = Modifier.layoutId(RegisterConstrainsIds.BTN_REGISTER),
//                enabled = state.enableRegisterButton(),
//                onClick = {
////                    if (rsFunctions.isValidInput(
////                            passwordTextState.text.toString(),
////                            emailTextState.text.toString(),
////                            userNameTextState.text.toString()
////                        )
////                    ) {
////                        auth.createUserAccount(
////                            registerScope,
////                            emailTextState.text.toString(),
////                            passwordTextState.text.toString()
////                        )
////                    } else {
////                        Log.i("RegisterScreen", "Failed to create account")
////                    }
////
////                    auth.createUserAccount(
////                        registerScope,
////                        emailTextState.toString(),
////                        passwordTextState.toString()
////                    )
//                })
//            {
//                Text(text = "Register")
//            }
//            LegalNotice(navController = navController)
//        }
//    }
//}
//
//
//
//@Composable
//private fun RegisterUsernameTF(
//    usernameValue: String,
//    onUsernameChanged: (String) -> Unit,
//    error: String?,
//    focusManager: FocusManager,
//    constraintId: Any
//) {
//    Column(modifier = Modifier.layoutId(constraintId)) {
//        OutlinedTextField(
//            value = usernameValue,
//            onValueChange = { onUsernameChanged(it) },
//            label = { Text(text = "username") },
//            singleLine = true,
//            keyboardOptions = KeyboardOptions(
//                keyboardType = KeyboardType.Text,
//                imeAction = ImeAction.Next
//            ),
//            keyboardActions = KeyboardActions(
//                onNext = { focusManager.moveFocus(FocusDirection.Down) }
//            ),
//            isError = error != null
//        )
//
//        error?.let { ErrorField(it) }
//    }
//}
//
//@Composable
//private fun RegisterEmailTF(
//    emailValue: String,
//    onEmailChanged: (String) -> Unit,
//    error: String?,
//    focusManager: FocusManager,
//    constraintId: Any
//) {
//    Column(modifier = Modifier.layoutId(constraintId)) {
//        OutlinedTextField(
//            value = emailValue,
//            onValueChange = { onEmailChanged(it) },
//            label = { Text(text = "email") },
//            singleLine = true,
//            keyboardOptions = KeyboardOptions(
//                keyboardType = KeyboardType.Email,
//                imeAction = ImeAction.Next
//            ),
//            keyboardActions = KeyboardActions(
//                onNext = { focusManager.moveFocus(FocusDirection.Down) }
//            ),
//            isError = error != null
//        )
//
//        error?.let { ErrorField(it) }
//    }
//}
//
//
//@Composable
//private fun RegisterPasswordTF(
//    passwordValue: String,
//    onPasswordChanged: (String) -> Unit,
//    error: String?,
//    focusManager: FocusManager,
//    constraintId: Any
//) {
//    var showPassword by remember { mutableStateOf(false) }
//
//    Column(modifier = Modifier.layoutId(constraintId)) {
//        OutlinedTextField(
//            value = passwordValue,
//            onValueChange = { onPasswordChanged(it) },
//            label = { Text(text = "password") },
//            singleLine = true,
//            keyboardOptions = KeyboardOptions(
//                imeAction = ImeAction.Next
//            ),
//            keyboardActions = KeyboardActions(
//                onNext = { focusManager.moveFocus(FocusDirection.Down) }
//            ),
//            isError = error != null,
//            visualTransformation = if (showPassword) {
//                VisualTransformation.None
//            } else {
//                PasswordVisualTransformation()
//            },
//            trailingIcon = {
//                if (showPassword) {
//                    IconButton(onClick = { showPassword = false }) {
//                        Icon(
//                            imageVector = Icons.Filled.Visibility,
//                            contentDescription = "hide password"
//                        )
//                    }
//                } else {
//                    IconButton(onClick = { showPassword = true }) {
//                        Icon(
//                            imageVector = Icons.Filled.VisibilityOff,
//                            contentDescription = "show password"
//                        )
//                    }
//                }
//            }
//        )
//
//        error?.let { ErrorField(it) }
//    }
//}
//
//
//@Composable
//private fun RegisterConfirmPasswordTF(
//    confirmPasswordValue: String,
//    onConfirmPasswordChanged: (String) -> Unit,
//    error: String?,
//    focusManager: FocusManager,
//    constraintId: Any
//) {
//    Column(modifier = Modifier.layoutId(constraintId)) {
//        OutlinedTextField(
//            value = confirmPasswordValue,
//            onValueChange = { onConfirmPasswordChanged(it) },
//            label = { Text(text = "confirm password") },
//            singleLine = true,
//            keyboardOptions = KeyboardOptions(
//                keyboardType = KeyboardType.Text,
//                imeAction = ImeAction.Next
//            ),
//            keyboardActions = KeyboardActions(
//                onNext = { focusManager.moveFocus(FocusDirection.Down) }
//            ),
//            isError = error != null
//        )
//
//        error?.let { ErrorField(it) }
//    }
//}
//
//
//@Composable
//private fun LegalNotice(
//    navController: NavController
//) {
//    Row(modifier = Modifier.layoutId(RegisterConstrainsIds.ROW_NOTICE)) {
//        Text(
//            text = "By registering you agree to our ",
//            color = Color.Gray,
//            fontSize = MaterialTheme.typography.body1.fontSize,
//            fontWeight = FontWeight.Bold
//        )
//    }
//    Row(modifier = Modifier.layoutId(RegisterConstrainsIds.ROW_POLICIES)) {
//        Text(
//            modifier = Modifier.clickable {
//                navController.navigate(route = PolicyScreen.TermsOfService.route)
//            },
//            text = "Terms of service ",
//            color = Color.Gray,
//            fontSize = MaterialTheme.typography.body1.fontSize,
//            fontWeight = FontWeight.Bold
//        )
//        Text(
//            text = "and ",
//            color = Color.Gray,
//            fontSize = MaterialTheme.typography.body1.fontSize,
//            fontWeight = FontWeight.Bold
//        )
//        Text(
//            modifier = Modifier.clickable {
//                navController.navigate(route = PolicyScreen.PrivacyPolicy.route)
//            },
//            text = "Privacy Policy",
//            color = Color.Gray,
//            fontSize = MaterialTheme.typography.body1.fontSize,
//            fontWeight = FontWeight.Bold
//        )
//    }
//}
//
//
//@Composable
//private fun AccountInfoText(
//    accountQuestion: String,
//    actionQuestion: String,
//    modifier: Modifier,
//    navigateModifier: Modifier
//) {
//    Row(modifier = modifier) {
//        Text(
//            text = accountQuestion,
//            color = Color.Gray,
//            fontSize = MaterialTheme.typography.body1.fontSize,
//            fontWeight = FontWeight.Bold
//        )
//        Text(
//            modifier = navigateModifier,
//            text = actionQuestion,
//            color = Color.Gray,
//            fontSize = MaterialTheme.typography.body1.fontSize,
//            fontWeight = FontWeight.Bold
//        )
//    }
//}
//
//
//private fun registrerPhoneContraints(): ConstraintSet {
//    return ConstraintSet {
//        val topGuideline = createGuidelineFromTop(0.24f)
//        val txtTitle = createRefFor(RegisterConstrainsIds.TXT_TITLE)
//        val tfUsername = createRefFor(RegisterConstrainsIds.TF_USERNAME)
//        val tfEmail = createRefFor(RegisterConstrainsIds.TF_EMAIL)
//        val tfPassword = createRefFor(RegisterConstrainsIds.TF_PASSWORD)
//        val tfConfirmPassword = createRefFor(RegisterConstrainsIds.TF_CONFIRM_PASSWORD)
//        val rowAccountInfo = createRefFor(RegisterConstrainsIds.ROW_ACCOUNT_INFO)
//        val btnRegister = createRefFor(RegisterConstrainsIds.BTN_REGISTER)
//        val rowLegalInfo = createRefFor(RegisterConstrainsIds.ROW_NOTICE)
//        val rowPolicies = createRefFor(RegisterConstrainsIds.ROW_POLICIES)
//
//
//        constrain(txtTitle) {
//            top.linkTo(anchor = topGuideline)
//            start.linkTo(anchor = parent.start)
//            end.linkTo(anchor = parent.end)
//        }
//        constrain(tfUsername) {
//            top.linkTo(anchor = txtTitle.bottom, margin = 16.dp)
//            start.linkTo(anchor = parent.start)
//            end.linkTo(anchor = parent.end)
//        }
//        constrain(tfEmail) {
//            top.linkTo(anchor = tfUsername.bottom, margin = 16.dp)
//            start.linkTo(anchor = parent.start)
//            end.linkTo(anchor = parent.end)
//        }
//        constrain(tfPassword) {
//            top.linkTo(anchor = tfEmail.bottom, margin = 16.dp)
//            start.linkTo(anchor = parent.start)
//            end.linkTo(anchor = parent.end)
//        }
//        constrain(tfConfirmPassword) {
//            top.linkTo(anchor = tfPassword.bottom, margin = 16.dp)
//            start.linkTo(anchor = parent.start)
//            end.linkTo(anchor = parent.end)
//        }
//        constrain(rowAccountInfo) {
//            top.linkTo(anchor = tfConfirmPassword.bottom, margin = 8.dp)
//            start.linkTo(anchor = tfConfirmPassword.start)
//            end.linkTo(anchor = tfConfirmPassword.end)
//        }
//        constrain(btnRegister) {
//            top.linkTo(anchor = rowAccountInfo.bottom, margin = 16.dp)
//            start.linkTo(anchor = parent.start)
//            end.linkTo(anchor = parent.end)
//        }
//        constrain(rowLegalInfo) {
//            bottom.linkTo(anchor = rowPolicies.top)
//            start.linkTo(anchor = tfPassword.start)
//            end.linkTo(anchor = tfPassword.end)
//        }
//        constrain(rowPolicies) {
//            bottom.linkTo(anchor = parent.bottom)
//            start.linkTo(anchor = tfPassword.start)
//            end.linkTo(anchor = tfPassword.end)
//        }
//    }
//}
//
////private fun tabletContraints(): ConstraintSet {}
//
//
//@Preview(showBackground = true)
//@Composable
//private fun registerPreview() {
//    EggNationTheme {
//        RegisterScreen(rememberNavController())
//    }
//}
//
//
//class RegisterScreenFunctions {
//    private val firestore: Firestore = Firestore()
//    private val auth: Authentication = Authentication()
//
//    /**
//     * Queries the database (IDK which one yet) to see if username exists already
//     * @return (Boolean) Whether the username inputted is unique or not
//     */
//    private fun isUniqueUsername(userUserName: String): Boolean {
////        val userUserName = binding.etRegisterUsername.text.toString()
//        // TODO - iterate through a list of usernames in the database and check for uniqueness
//        return true
//    }
//
//
//}