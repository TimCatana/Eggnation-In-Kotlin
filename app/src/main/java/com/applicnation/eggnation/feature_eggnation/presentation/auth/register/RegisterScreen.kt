package com.applicnation.eggnation.feature_eggnation.presentation.auth.register

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.applicnation.eggnation.feature_eggnation.presentation.navigation.GameScreen
import com.applicnation.eggnation.feature_eggnation.presentation.navigation.PolicyScreen
import com.applicnation.eggnation.ui.theme.EggNationTheme
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

//TODO - use string resources

@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: RegisterScreenViewModel = hiltViewModel()
) {
    val interactionSource = remember { MutableInteractionSource() }

    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is RegisterScreenViewModel.UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                    )
                }
                is RegisterScreenViewModel.UiEvent.ChangeStacks -> {
                    navController.navigate(GameScreen.Home.route)
                }
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
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
                    text = "Register Screen",
                    style = MaterialTheme.typography.h5
                )
                Spacer(modifier = Modifier.height(16.dp))
                StandardTextField(
                    text = viewModel.usernameText.value,
                    onValueChange = {
                        viewModel.onEvent(RegisterScreenEvent.EnteredUsername(it))
                    },
                    isError = viewModel.isUsernameError.value,
                    errorText = "Username must be at least 5 characters long",
                    label = "username" // use string resources
                )
                Spacer(modifier = Modifier.height(16.dp))
                StandardTextField(
                    text = viewModel.emailText.value,
                    onValueChange = {
                        viewModel.onEvent(RegisterScreenEvent.EnteredEmail(it))
                    },
                    isError = viewModel.isEmailError.value,
                    errorText = "Invalid email address",
                    label = "email" // use string resources
                )
                Spacer(modifier = Modifier.height(16.dp))
                StandardTextField(
                    text = viewModel.passwordText.value,
                    onValueChange = {
                        viewModel.onEvent(RegisterScreenEvent.EnteredPassword(it))
                    },
                    isError = viewModel.isPasswordError.value,
                    errorText = "Password must be at least 8 characters, contain no whitespace and have numbers, lowercase and uppercase letters",
                    label = "password",
                    keyboardType = KeyboardType.Password,
                )
                Spacer(modifier = Modifier.height(16.dp))
                StandardTextField(
                    text = viewModel.confirmPasswordText.value,
                    onValueChange = {
                        viewModel.onEvent(RegisterScreenEvent.EnteredConfirmPassword(it))
                    },
                    isError = viewModel.isConfirmPasswordError.value,
                    errorText = "Passwords must match!",
                    keyboardType = KeyboardType.Password,
                    label = "confirm password" // use string resources
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    enabled = !viewModel.isUsernameError.value
                            && !viewModel.isEmailError.value
                            && !viewModel.isPasswordError.value
                            && !viewModel.isConfirmPasswordError.value,
                    onClick = {
                        viewModel.onEvent(
                            RegisterScreenEvent.SignUp(
                                viewModel.emailText.value,
                                viewModel.passwordText.value,
                                viewModel.usernameText.value
                            )
                        )
                    },
                ) {
                    Text(text = "Register")
                }
            }

            Column(modifier = Modifier
                .align(Alignment.BottomCenter)
                .wrapContentHeight()) {
                Row(
                    modifier = Modifier
                        .align(alignment = Alignment.CenterHorizontally)
                ) {
                    Text(text = "By registering you agree to our" )

                }
                Row(
                    modifier = Modifier
                        .align(alignment = Alignment.CenterHorizontally)
                ) {
                    Text(
                        text = "Privacy Policy",
                        color = Color.Blue,
                        modifier = Modifier.clickable(
                            interactionSource = interactionSource,
                            indication = null
                        ) {
                            navController.navigate(
                                PolicyScreen.PrivacyPolicy.route
                            )
                        })
                    Text(text = " and ")
                    Text(
                        text = "Terms and Conditions",
                        color = Color.Blue,
                        modifier = Modifier.clickable(
                            interactionSource = interactionSource,
                            indication = null
                        ) {
                            navController.navigate(
                                PolicyScreen.TermsOfService.route
                            )
                        })
                }
            }
//            Text(
//                text = buildAnnotatedString {
//                    append("Already have an account?")
//                    append(" ")
//                    // TODO - for string resources, create a val here and put the val in the with styles cause you can't accesss strng rersources from within withStyle
//                    withStyle(
//                        style = SpanStyle(
//                            color = MaterialTheme.colors.primary
//                        )
//                    ) {
//                        append("Login")
//                    }
//                },
//                style = MaterialTheme.typography.body1,
//                modifier = Modifier
//                    .align(Alignment.BottomCenter)
//                    .clickable {
//                        navController.navigate(
//                            AuthScreen.Login.route
//                        )
//                    } // TODO - need to pop backstack when going back to login screen
//            )

            if (viewModel.isLoading.value) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun registerPreview() {
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
                    text = "Register Screen",
                    style = MaterialTheme.typography.h5
                )
                Spacer(modifier = Modifier.height(16.dp))
                StandardTextField(
                    text = "test",
                    onValueChange = {
                    },
                    label = "username" // use string resources
                )
                Spacer(modifier = Modifier.height(16.dp))
                StandardTextField(
                    text = "test",
                    onValueChange = {
                    },
                    label = "email" // use string resources
                )
                Spacer(modifier = Modifier.height(16.dp))
                StandardTextField(
                    text = "test",
                    onValueChange = {
                    },
                    keyboardType = KeyboardType.Password,
                    label = "password" // use string resources
                )
                Spacer(modifier = Modifier.height(16.dp))
                StandardTextField(
                    text = "test",
                    onValueChange = {
                    },
                    keyboardType = KeyboardType.Password,
                    label = "confirm password" // use string resources
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { /*TODO*/ },
                ) {
                    Text(text = "Register")
                }
            }
            Text(
                text = buildAnnotatedString {
                    append("Already have an account?")
                    append(" ")
                    // TODO - for string resources, create a val here and put the val in the with styles cause you can't accesss strng rersources from within withStyle
                    withStyle(
                        style = SpanStyle(
                            color = MaterialTheme.colors.primary
                        )
                    ) {
                        append("Login")
                    }
                },
                style = MaterialTheme.typography.body1,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
}
