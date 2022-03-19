package com.applicnation.eggnation.feature_eggnation.presentation.edit_settings.edit_password

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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.applicnation.eggnation.feature_eggnation.presentation.components.PasswordModal
import com.applicnation.eggnation.feature_eggnation.presentation.components.StandardTextField
import com.applicnation.eggnation.feature_eggnation.presentation.navigation.GameScreen
import com.applicnation.eggnation.ui.theme.SettingsBG
import kotlinx.coroutines.flow.collectLatest

// TODO - make text box outline blue

@Composable
fun EditPasswordScreen(
    navController: NavController,
    viewModel: EditPasswordScreenViewModel = hiltViewModel()

) {
    val scaffoldState = rememberScaffoldState()
    val localFocusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is EditPasswordScreenViewModel.UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                    )
                }
                is EditPasswordScreenViewModel.UiEvent.ChangeStacks -> {
                    navController.navigate(GameScreen.Settings.route) {
                        popUpTo(GameScreen.Home.route)
                    }
                }
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        backgroundColor = SettingsBG
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
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 40.dp)
            ) {
                Column(
                    modifier = Modifier.align(Alignment.Center)
                ) {
                    StandardTextField(
                        text = viewModel.passwordText.value,
                        onValueChange = {
                            viewModel.onEvent(EditPasswordScreenEvent.EnteredPassword(it))
                        },
                        isError = viewModel.isPasswordError.value,
                        errorText = "Lowercase, uppsercase numbers 8 chars",
                        label = "new password",
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(focusRequester),
                        isPassword = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = {
                                localFocusManager.moveFocus(FocusDirection.Down)
                            }
                        ),
                        textFieldColors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color.Blue,
                            focusedLabelColor = Color.Blue,
                            unfocusedBorderColor = Color.White,
                            unfocusedLabelColor = Color.White,
                            trailingIconColor = Color.White,
                            cursorColor = Color.Blue,
                            textColor = Color.White
                        ),
                        isIconClickable = true
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    StandardTextField(
                        // TODO - keep this disabled until the password is equal to the current password
                        text = viewModel.confirmPasswordText.value,
                        onValueChange = {
                            viewModel.onEvent(EditPasswordScreenEvent.EnteredConfirmPassword(it))
                        },
                        isError = viewModel.isConfirmPasswordError.value,
                        errorText = "passwords must match",
                        label = "confirm password",
                        modifier = Modifier.fillMaxWidth(),
                        isPassword = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                localFocusManager.clearFocus()
                            }
                        ),
                        textFieldColors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color.Blue,
                            focusedLabelColor = Color.Blue,
                            unfocusedBorderColor = Color.White,
                            unfocusedLabelColor = Color.White,
                            trailingIconColor = Color.White,
                            cursorColor = Color.Blue,
                            textColor = Color.White
                        ),
                        isIconClickable = true
                    )
                }
                Button(
                    enabled = !viewModel.isPasswordError.value && !viewModel.isConfirmPasswordError.value,
                    onClick = {
                        viewModel.onEvent(EditPasswordScreenEvent.EnteredValidationPassword(""))
                        viewModel.onEvent(EditPasswordScreenEvent.ShowPasswordModel(true))
                    },
                    modifier = Modifier.align(Alignment.BottomCenter)
                ) {
                    Text(text = "Change Password")
                }
            }

            if (viewModel.isPasswordModelShowing.value) {
                PasswordModal(
                    // TODO - make width and height based on screen dimensions
                    modifier = Modifier
                        .align(Alignment.Center)
                        .width(300.dp)
                        .height(200.dp),
                    cancelModal = {
                        viewModel.onEvent(EditPasswordScreenEvent.ShowPasswordModel(false))
                    },
                    onDone = { viewModel.onEvent(EditPasswordScreenEvent.UpdatePassword(viewModel.confirmPasswordText.value)) },
                    onTextChange = {
                        viewModel.onEvent(
                            EditPasswordScreenEvent.EnteredValidationPassword(
                                it
                            )
                        )
                    },
                    text = viewModel.validationPasswordText.value,
                )
            }

            if (viewModel.isLoading.value) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PasswordScreenPreview() {
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
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 40.dp)
            ) {
                Column(
                    modifier = Modifier.align(Alignment.Center)
                ) {
                    StandardTextField(
                        text = "test",
                        onValueChange = {},
                        isError = false,
                        errorText = "Invalid email address",
                        label = "password",
                        modifier = Modifier.fillMaxWidth(),
                        isIconClickable = true
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    StandardTextField(
                        text = "test",
                        onValueChange = {},
                        isError = false,
                        errorText = "Invalid email address",
                        label = "new password",
                        modifier = Modifier.fillMaxWidth(),
                        isIconClickable = true
                    )
                }
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