package com.applicnation.eggnation.feature_eggnation.presentation.auth.register

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.applicnation.eggnation.feature_eggnation.presentation.auth.login.LoginScreenEvent
import com.applicnation.eggnation.feature_eggnation.presentation.components.EmailTextField
import com.applicnation.eggnation.feature_eggnation.presentation.components.PasswordTextField
import com.applicnation.eggnation.feature_eggnation.presentation.components.StandardTextField
import com.applicnation.eggnation.feature_eggnation.presentation.navigation.PolicyScreen
import com.applicnation.eggnation.util.WindowInfo
import com.applicnation.eggnation.util.rememberWindowInfo
import kotlinx.coroutines.flow.collectLatest

//TODO - use string resources

@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: RegisterScreenViewModel = hiltViewModel()
) {
    val windowInfo = rememberWindowInfo()
    val interactionSource = remember { MutableInteractionSource() }
    val localFocusManager = LocalFocusManager.current
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is RegisterScreenViewModel.UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                    )
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
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(text = windowInfo.screenWidthInfo.toString())

            if (windowInfo.screenWidthInfo is WindowInfo.WindowType.Compact) {
                RegisterScreenContent(
                    viewModel = viewModel,
                    navController = navController,
                    localFocusManager = localFocusManager,
                    interactionSource = interactionSource,
                    columnModifier = Modifier
                        .align(Alignment.BottomCenter)
                        .wrapContentHeight()
                )
            } else {
                Box(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .width(400.dp)
                        .fillMaxHeight()
                ) {
                    RegisterScreenContent(
                        viewModel = viewModel,
                        navController = navController,
                        localFocusManager = localFocusManager,
                        interactionSource = interactionSource,
                        columnModifier = Modifier
                            .align(Alignment.BottomCenter)
                            .wrapContentHeight()
                    )
                }
            }

            if (viewModel.isLoading.value) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}

@Composable
fun RegisterScreenContent(
    viewModel: RegisterScreenViewModel,
    navController: NavController,
    localFocusManager: FocusManager,
    interactionSource: MutableInteractionSource,
    columnModifier: Modifier
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
        EmailTextField(
            text = viewModel.emailText.value,
            onValueChange = { viewModel.onEvent(RegisterScreenEvent.EnteredEmail(it)) },
            isError = viewModel.isEmailError.value,
            imeAction = ImeAction.Next,
            keyboardActions = KeyboardActions(
                onNext = { localFocusManager.moveFocus(FocusDirection.Down) }
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        PasswordTextField(
            text = viewModel.passwordText.value,
            onValueChange = { viewModel.onEvent(RegisterScreenEvent.EnteredPassword(it)) },
            isError = viewModel.isPasswordError.value,
            imeAction = ImeAction.Next,
            keyboardActions = KeyboardActions(
                onNext = { localFocusManager.moveFocus(FocusDirection.Down) }
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        StandardTextField(
            text = viewModel.confirmPasswordText.value,
            onValueChange = {
                viewModel.onEvent(RegisterScreenEvent.EnteredConfirmPassword(it))
            },
            isError = viewModel.isConfirmPasswordError.value,
            errorText = "Passwords must match!",
            label = "confirm password", // use string resources
            modifier = Modifier.fillMaxWidth(),
            isPassword = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { localFocusManager.clearFocus() }
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            enabled = !viewModel.isEmailError.value
                    && !viewModel.isPasswordError.value
                    && !viewModel.isConfirmPasswordError.value,
            onClick = {
                localFocusManager.clearFocus()
                viewModel.onEvent(RegisterScreenEvent.SignUp)
            },
        ) {
            Text(text = "Register")
        }
    }

    Column(
        modifier = columnModifier
    ) {
        Row(
            modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
        ) {
            Text(text = "By registering you agree to our")

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
                    navController.navigate(PolicyScreen.TermsOfService.route)
                }
            )
        }
    }
}
