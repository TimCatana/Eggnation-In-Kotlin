package com.applicnation.eggnation.feature_eggnation.presentation.edit_settings.email_settings.edit_email

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
import kotlinx.coroutines.flow.collectLatest

@Composable
fun EditEmailScreen(
    navController: NavController,
    viewModel: EditEmailScreenViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is EditEmailScreenViewModel.UiEvent.ShowSnackbar -> {
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
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 40.dp)
            ) {
                Column() {
                    StandardTextField(
                        text = viewModel.emailText.value,
                        onValueChange = {
                            viewModel.onEvent(EditEmailScreenEvent.EnteredEmail(it))
                        },
                        isError = viewModel.isEmailError.value,
                        errorText = "Invalid email address",
                        label = "email",
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    StandardTextField(
                        text = viewModel.passwordText.value,
                        onValueChange = {
                            viewModel.onEvent(EditEmailScreenEvent.EnteredPassword(it))
                        },
                        isError = false,
                        errorText = "enter a valid password",
                        label = "password",
                    )
                }
                Button(
                    enabled = !viewModel.isEmailError.value, // TODO - check that password is filled in as well
                    onClick = {
                        viewModel.onEvent(
                            EditEmailScreenEvent.UpdateEmail(
                                viewModel.emailText.value,
                                viewModel.passwordText.value
                            )
                        )
                    },
                    modifier = Modifier.align(Alignment.BottomCenter)
                ) {
                    Text(text = "Change Email")
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun EmailScreenPreview() {
    val scaffoldState = rememberScaffoldState()

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
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 40.dp)
            ) {
                StandardTextField(
                    text = "test",
                    onValueChange = {},
                    isError = false,
                    errorText = "Invalid email address",
                    label = "email",
                )
                Button(
                    enabled = false,
                    onClick = {},
                    modifier = Modifier.align(Alignment.BottomCenter)
                ) {
                    Text(text = "Change Email")
                }
            }
        }
    }
}