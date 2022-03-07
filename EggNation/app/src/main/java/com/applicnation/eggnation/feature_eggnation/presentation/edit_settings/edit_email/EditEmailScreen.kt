package com.applicnation.eggnation.feature_eggnation.presentation.edit_settings.edit_email

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.applicnation.eggnation.feature_eggnation.presentation.components.StandardTextField
import com.applicnation.eggnation.feature_eggnation.presentation.edit_settings.edit_password.EditPasswordScreenEvent
import com.applicnation.eggnation.feature_eggnation.presentation.game.settings.SettingsScreenEvent
import com.applicnation.eggnation.feature_eggnation.presentation.game.settings.SettingsScreenViewModel
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
                }
                Button(
                    enabled = !viewModel.isEmailError.value, // TODO - check that password is filled in as well
                    onClick = {
                        viewModel.onEvent(
                            EditEmailScreenEvent.ShowPasswordModel(true, "")
                        )
                    },
                    modifier = Modifier.align(Alignment.BottomCenter)
                ) {
                    Text(text = "Change Email")
                }
            }

            if (viewModel.isPasswordModelShowing.value) {
                PasswordModelsss(
                    viewModel = viewModel,
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .background(Color.White)
                        .width(400.dp)
                        .height(400.dp), // TODO - make width and height based on screen dimensions
                )
            }
        }
    }
}


@Composable
private fun PasswordModelsss(
    viewModel: EditEmailScreenViewModel,
    modifier: Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Filled.Close,
            contentDescription = "exit",
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 4.dp, top = 4.dp)
                .clickable {
                    viewModel.onEvent(EditEmailScreenEvent.EnteredPassword(""))
                    viewModel.onEvent(EditEmailScreenEvent.ShowPasswordModel(false, ""))
                }
        )
        StandardTextField(
            text = viewModel.passwordText.value,
            onValueChange = {
                viewModel.onEvent(EditEmailScreenEvent.EnteredPassword(it))
            },
            isError = false,
            errorText = "Please enter a password",
            keyboardType = KeyboardType.Password,
            label = "password" // TODO - use string resources
        )
        Button(onClick = {
            viewModel.onEvent(
                EditEmailScreenEvent.UpdateEmail(
                    viewModel.emailText.value,
                )
            )
        }) {
            Text(text = "Confirm")
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