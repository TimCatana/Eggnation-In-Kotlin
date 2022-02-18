package com.applicnation.eggnation.feature_eggnation.presentation.auth.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.applicnation.eggnation.feature_eggnation.presentation.components.StandardTextField
import com.applicnation.eggnation.feature_eggnation.presentation.navigation.AuthScreen
import com.applicnation.eggnation.ui.theme.EggNationTheme


// TODO - navigate to register screen when "Sign up" text is clicked
@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginScreenViewModel = hiltViewModel()
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = 24.dp,
                end = 24.dp,
                top = 24.dp,
                bottom = 50.dp
            )
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Login Screen",
                style = MaterialTheme.typography.h5
            )
            Spacer(modifier = Modifier.height(16.dp))
            StandardTextField(
                text = viewModel.emailText.value,
                maxLength = 75,
                onValueChange = {
                    viewModel.onEvent(LoginScreenEvent.EnteredEmail(it))
                },
                isError = viewModel.isEmailError.value,
                errorText = "Invalid email address",
                label = "email" // TODO - use string resources
            )
            Spacer(modifier = Modifier.height(16.dp))
            StandardTextField(
                text = viewModel.passwordText.value,
                onValueChange = {
                    viewModel.onEvent(LoginScreenEvent.EnteredPassword(it))
                },
                isError = viewModel.isPasswordError.value,
                errorText = "Please enter a password",
                keyboardType = KeyboardType.Password,
                label = "password" // TODO - use string resources
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "forgot password?",
                modifier = Modifier
                    .align(Alignment.End)
                    .clickable {
                        navController.navigate(AuthScreen.ForgotPassword.route)
                    }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                enabled = !viewModel.isEmailError.value && !viewModel.isPasswordError.value,
                onClick = {
                    viewModel.onEvent(
                        LoginScreenEvent.SignIn(
                            viewModel.emailText.value,
                            viewModel.passwordText.value
                            // TODO - need to propogate error here
                        )
                    )
                },
            ) {
                Text(text = "Login")
            }
        }
        Text(
            text = buildAnnotatedString {
                append("Don't have an account yet?")
                append(" ")
                // TODO - for string resources, create a val here and put the val in the with styles cause you can't accesss strng rersources from within withStyle
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colors.primary
                    )
                ) {
                    append("Sign up")
                }
            },
            style = MaterialTheme.typography.body1,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .clickable {
                    navController.navigate(
                        AuthScreen.Register.route
                    )
                }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun loginPreview() {
    EggNationTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = 24.dp,
                    end = 24.dp,
                    top = 24.dp,
                    bottom = 50.dp
                )
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Login Screen",
                    style = MaterialTheme.typography.h5
                )
                Spacer(modifier = androidx.compose.ui.Modifier.height(16.dp))
                StandardTextField(
                    text = "test",
                    onValueChange = {
                    },
                    label = "email" // use string resources
                )
                Spacer(modifier = androidx.compose.ui.Modifier.height(16.dp))
                StandardTextField(
                    text = "test",
                    onValueChange = {
                    },
                    keyboardType = KeyboardType.Password,
                    label = "password" // use string resources
                )
                Spacer(modifier = androidx.compose.ui.Modifier.height(16.dp))
                Button(
                    onClick = { /*TODO*/ },
                ) {
                    Text(text = "Login")
                }
            }
            Text(
                text = buildAnnotatedString {
                    append("Don't have an account yet?")
                    append(" ")
                    // TODO - for string resources, create a val here and put the val in the with styles cause you can't accesss strng rersources from within withStyle
                    withStyle(
                        style = SpanStyle(
                            color = MaterialTheme.colors.primary
                        )
                    ) {
                        append("Sign up")
                    }
                },
                style = MaterialTheme.typography.body1,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
}
