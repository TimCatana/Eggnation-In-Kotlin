package com.applicnation.eggnation.ui.auth.stack.loginScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.applicnation.eggnation.composables.ErrorField
import com.applicnation.eggnation.navigation.AuthScreen
import com.applicnation.eggnation.ui.auth.stack.common.composables.HeaderText
import com.applicnation.eggnation.ui.auth.stack.common.states.LoginTextFieldStates
import com.applicnation.eggnation.ui.theme.EggNationTheme

private enum class LoginConstraintIds {
    TXT_TITLE,
    TF_EMAIL,
    TF_PASSWORD,
    ROW_INFO,
    BTN_LOGIN,
}

@Composable
fun LoginScreen(
    navController: NavController
) {
    var state = remember { LoginTextFieldStates() }

    val localFocusManager = LocalFocusManager.current

    BoxWithConstraints() {
        val constraints = loginPhoneContraints()

        ConstraintLayout(constraints, modifier = Modifier.fillMaxSize()) {
            HeaderText(
                text = "Login", // TODO - set a string resource
                constraintId = LoginConstraintIds.TXT_TITLE
            )
            LoginEmailTF(
                constraintId = LoginConstraintIds.TF_EMAIL,
                emailValue = state.emailText,
                onEmailChanged = {
                    state.emailText = it
                    state.validateEmail()
//                    state.enableLoginButton()
                },
                error = state.emailError,
                focusManager = localFocusManager
            )
            LoginPasswordTF(
                constraintId = LoginConstraintIds.TF_PASSWORD,
                passwordValue = state.passwordText,
                onPasswordChanged = {
                    state.passwordText = it
                    state.validatePassword()
//                    state.enableLoginButton()
                },
                error = state.passwordError,
                focusManager = localFocusManager,
            )
            AccountInfoText(
                accountQuestion = "Don't have an account? ",
                actionQuestion = "Register here",
                modifier = Modifier.layoutId(LoginConstraintIds.ROW_INFO),
                navigateModifier = Modifier.clickable {
                    navController.navigate(route = AuthScreen.Register.route)
                },
            )
            Button(
                modifier = Modifier.layoutId(LoginConstraintIds.BTN_LOGIN),
                onClick = {/* TODO */ },
                enabled = state.enableLoginButton() // FIXME - this may or may not be dangerous due to the nature of recompositions. I may just make this function run whenever a textfield is changed and make that change a state varaible
            )
            {
                Text(text = "Login")
            }
        }
    }
}


@Composable
private fun LoginEmailTF(
    emailValue: String,
    onEmailChanged: (String) -> Unit,
    error: String?,
    focusManager: FocusManager,
    constraintId: Any
) {
    Column(modifier = Modifier.layoutId(constraintId)) {
        OutlinedTextField(
            value = emailValue,
            onValueChange = { onEmailChanged(it) },
            label = { Text(text = "Email") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            ),
            isError = error != null
        )

        error?.let { ErrorField(it) }
    }
}


@Composable
private fun LoginPasswordTF(
    passwordValue: String,
    onPasswordChanged: (String) -> Unit,
    error: String?,
    focusManager: FocusManager,
    constraintId: Any
) {
    var showPassword by remember { mutableStateOf(false) }

    Column(modifier = Modifier.layoutId(constraintId)) {
        OutlinedTextField(
            value = passwordValue,
            onValueChange = { onPasswordChanged(it) },
            label = { Text(text = "password") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            ),
            isError = error != null,
            visualTransformation = if (showPassword) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            trailingIcon = {
                if (showPassword) {
                    IconButton(onClick = { showPassword = false }) {
                        Icon(
                            imageVector = Icons.Filled.Visibility,
                            contentDescription = "hide password"
                        )
                    }
                } else {
                    IconButton(onClick = { showPassword = true }) {
                        Icon(
                            imageVector = Icons.Filled.VisibilityOff,
                            contentDescription = "show password"
                        )
                    }
                }
            }
        )

        error?.let { ErrorField(it) }
    }
}


@Composable
private fun AccountInfoText(
    accountQuestion: String,
    actionQuestion: String,
    modifier: Modifier,
    navigateModifier: Modifier
) {
    Row(modifier = modifier) {
        Text(
            text = accountQuestion,
            color = Color.Gray,
            fontSize = MaterialTheme.typography.body1.fontSize,
            fontWeight = FontWeight.Bold
        )
        Text(
            modifier = navigateModifier,
            text = actionQuestion,
            color = Color.Gray,
            fontSize = MaterialTheme.typography.body1.fontSize,
            fontWeight = FontWeight.Bold
        )
    }
}


private fun loginPhoneContraints(): ConstraintSet {
    return ConstraintSet {
        val topGuideline = createGuidelineFromTop(0.34f)
        val txtTitle = createRefFor(LoginConstraintIds.TXT_TITLE)
        val tfEmail = createRefFor(LoginConstraintIds.TF_EMAIL)
        val tfPassword = createRefFor(LoginConstraintIds.TF_PASSWORD)
        val rowInfo = createRefFor(LoginConstraintIds.ROW_INFO)
        val btnLogin = createRefFor(LoginConstraintIds.BTN_LOGIN)

        constrain(txtTitle) {
            top.linkTo(anchor = topGuideline)
            start.linkTo(anchor = parent.start)
            end.linkTo(anchor = parent.end)
        }
        constrain(tfEmail) {
            top.linkTo(anchor = txtTitle.bottom, margin = 32.dp)
            start.linkTo(anchor = parent.start)
            end.linkTo(anchor = parent.end)
        }
        constrain(tfPassword) {
            top.linkTo(anchor = tfEmail.bottom, margin = 32.dp)
            start.linkTo(anchor = parent.start)
            end.linkTo(anchor = parent.end)
        }
        constrain(rowInfo) {
            top.linkTo(anchor = tfPassword.bottom, margin = 8.dp)
            start.linkTo(anchor = tfPassword.start)
            end.linkTo(anchor = tfPassword.end)
        }
        constrain(btnLogin) {
            top.linkTo(anchor = rowInfo.bottom, margin = 16.dp)
            start.linkTo(anchor = parent.start)
            end.linkTo(anchor = parent.end)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun loginPreview() {
    EggNationTheme {
        LoginScreen(rememberNavController())
    }
}


//private lateinit var auth: FirebaseAuth
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_login)
//
//        auth = FirebaseAuth.getInstance()
//
//        var registerTV = findViewById<TextView>(R.id.tvRegister)
//        registerTV.setOnClickListener {
//
//            val goToRegisterActivity = Intent(this@LoginActivity, RegisterActivity::class.java)
//            goToRegisterActivity.putExtra("key", "Sent String")
//            startActivity(goToRegisterActivity)
//        }
//
//        var loginBtn = findViewById<Button>(R.id.btnLogin)
//        var emailTV = findViewById<EditText>(R.id.etLoginEmail)
//        var passwordTV = findViewById<EditText>(R.id.etLoginPassword)
//
//        loginBtn.setOnClickListener {
//            login(email = emailTV.text.toString(), password = passwordTV.text.toString())
//        }
//
//
//
//        // to use the onclick fun i creacte below you need to add:
//        // <ViewId>.setOnCLickListener(this) for each component
//
//
//    }
//
//
//
//
//
//    private fun login(email: String?, password: String?) {
//        if(email === null || password === null) {
//            Toast.makeText(this@LoginActivity, "Something went wrong while reading email and password", Toast.LENGTH_LONG).show()
//        }
//
//        // TODO - check to make sure inputs are valid and sanatized
//
//        auth.signInWithEmailAndPassword(email!!, password!!)
//            .addOnCompleteListener {
//                if(it.isSuccessful) {
//                    // Add user to firestore database
//                    Toast.makeText(this@LoginActivity, "Successfully Logged In ", Toast.LENGTH_LONG).show()
//                    startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
//                    finish()
//                } else {
//                    Toast.makeText(this@LoginActivity, "Failed to register user. Please try again", Toast.LENGTH_LONG).show()
//                }
//            }
//    }
//
//
//
//
//
//    private fun validateLoginDetails() : Boolean {
//
//        // no spaces before and after.
//        // passwords must match
//        // password > 8 chars
//        // password contain Caps, no caps, and number
//        // username must be unique (use firebase database to check this), prob gonna need a corroutine to await the process.
//        // check if is valid email format? is there a library for this? If not, The google auth will fail anyway.
//
//
//        return when {
//            TextUtils.isEmpty(findViewById<EditText>(R.id.etLoginEmail).text.toString()) -> {
//                showErrorSnackBar("error message", true)
//                // will probably use showErrorSnackBar(resources.getString(R.string.name_of_string_from_res, true)
//                false
//            }
//            /**
//             * !checkbox.ischekced -> {
//             *  show error snackbar
//             * }
//             */
//            // Need to add terms and conditions/ privacy policy acceptance
//            // Add checkbox for EU people to accept me showing them ads. Makes it simpler
//
//            // for else statement, return true because the input is valie
//            // once returned true, create the firebase auth account
//            else -> false
//        }
//
//    }
//}