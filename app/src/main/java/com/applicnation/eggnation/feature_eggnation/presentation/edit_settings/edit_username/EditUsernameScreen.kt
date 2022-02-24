package com.applicnation.eggnation.feature_eggnation.presentation.edit_settings.edit_username

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
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
fun EditUsernameScreen(
    navController: NavController,
    viewModel: EditUsernameScreenViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is EditUsernameScreenViewModel.UiEvent.ShowSnackbar -> {
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
                        text = viewModel.usernameText.value,
                        onValueChange = {
                            viewModel.onEvent(EditUsernameScreenEvent.EnteredUsername(it))
                        },
                        isError = viewModel.isUsernameError.value,
                        errorText = "Username must be greater than 5 characters",
                        label = "username",
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    StandardTextField(
                        text = viewModel.passwordText.value,
                        onValueChange = {
                            viewModel.onEvent(EditUsernameScreenEvent.EnteredPassword(it))
                        },
                        isError = false,
                        errorText = "enter a valid password",
                        label = "password",
                    )
                }
                Button(
                    enabled = !viewModel.isUsernameError.value, // TODO - check that password is filled in as well
                    onClick = {
                        viewModel.onEvent(
                            EditUsernameScreenEvent.UpdateUsername(
                                viewModel.usernameText.value,
                                viewModel.passwordText.value
                            )
                        )
                    },
                    modifier = Modifier.align(Alignment.BottomCenter)
                ) {
                    Text(text = "Change Username")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UsernameScreenPreview() {
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
                    Text(text = "Change Username")
                }
            }
        }
    }
}