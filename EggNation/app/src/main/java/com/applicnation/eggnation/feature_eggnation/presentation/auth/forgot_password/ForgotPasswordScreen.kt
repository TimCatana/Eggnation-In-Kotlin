package com.applicnation.eggnation.feature_eggnation.presentation.auth.forgot_password

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.applicnation.eggnation.feature_eggnation.presentation.auth.register.RegisterScreenContent
import com.applicnation.eggnation.feature_eggnation.presentation.components.StandardTextField
import com.applicnation.eggnation.util.WindowInfo
import com.applicnation.eggnation.util.rememberWindowInfo
import kotlinx.coroutines.flow.collectLatest

//TODO - use string resources
//TODO - Add back arrow button and navigate to login screen when user taps it (remember to pop back stack)

@Composable
fun ForgotPasswordScreen(
    navController: NavController,
    viewModel: ForgotPasswordScreenViewModel = hiltViewModel()
) {
    val windowInfo = rememberWindowInfo()

    val scaffoldState = rememberScaffoldState()
    val localFocusManager = LocalFocusManager.current


    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is ForgotPasswordScreenViewModel.UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                        duration = SnackbarDuration.Short
                    )
                    navController.popBackStack()
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
                ForgotPasswordScreenContent(
                    viewModel = viewModel,
                    navController = navController,
                    localFocusManager = localFocusManager,
                )
            } else {
                Box(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .width(400.dp)
                        .fillMaxHeight()
                ) {
                    ForgotPasswordScreenContent(
                        viewModel = viewModel,
                        navController = navController,
                        localFocusManager = localFocusManager,
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
fun ForgotPasswordScreenContent(
    viewModel: ForgotPasswordScreenViewModel,
    navController: NavController,
    localFocusManager: FocusManager,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Forgot Password Screen",
            style = MaterialTheme.typography.h5
        )
        Spacer(modifier = Modifier.height(16.dp))
        StandardTextField(
            text = viewModel.emailText.value,
            onValueChange = {
                viewModel.onEvent(ForgotPasswordScreenEvent.EnteredEmail(it))
            },
            isError = viewModel.isEmailError.value,
            errorText = "Invalid email address",
            label = "email",
            modifier = Modifier.fillMaxWidth(),
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
            enabled = !viewModel.isEmailError.value,
            onClick = {
                localFocusManager.clearFocus()
                viewModel.onEvent(
                    ForgotPasswordScreenEvent.SendResetPasswordEmail(
                        viewModel.emailText.value
                    )
                )
            },
        ) {
            Text(text = "send email")
        }
    }
}
