package com.applicnation.eggnation.feature_eggnation.presentation.auth.forgot_password

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.applicnation.eggnation.feature_eggnation.presentation.components.StandardTextField
import com.applicnation.eggnation.ui.theme.EggNationTheme
import kotlinx.coroutines.flow.collectLatest

//TODO - use string resources
//TODO - Add back arrow button and navigate to login screen when user taps it (remember to pop back stack)

@Composable
fun ForgotPasswordScreen(
    navController: NavController,
    viewModel: ForgotPasswordScreenViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is ForgotPasswordScreenViewModel.UiEvent.ShowSnackbar -> {
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
                )
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 40.dp)
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
                    label = "email"
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    enabled = !viewModel.isEmailError.value,
                    onClick = {
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

            if (viewModel.isLoading.value) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun forgotPasswordPreview() {
    EggNationTheme {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 40.dp)
        ) {
            Text(
                text = "Forgot Password Screen",
                style = MaterialTheme.typography.h5
            )
            Spacer(modifier = Modifier.height(16.dp))
            StandardTextField(
                text = "test",
                onValueChange = {
                },
                label = "email" // use string resources
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { /*TODO*/ },
            ) {
                Text(text = "send email")
            }
        }
    }
}