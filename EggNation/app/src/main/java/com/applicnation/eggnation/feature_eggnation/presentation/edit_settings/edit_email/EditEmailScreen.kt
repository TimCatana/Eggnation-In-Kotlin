package com.applicnation.eggnation.feature_eggnation.presentation.edit_settings.edit_email

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.applicnation.eggnation.feature_eggnation.presentation.edit_settings.edit_password.EditPasswordScreenEvent
import com.applicnation.eggnation.feature_eggnation.presentation.game.settings.SettingsScreenEvent
import com.applicnation.eggnation.feature_eggnation.presentation.game.settings.SettingsScreenViewModel
import com.applicnation.eggnation.feature_eggnation.presentation.navigation.GameScreen
import com.applicnation.eggnation.ui.theme.SettingsBG
import kotlinx.coroutines.flow.collectLatest

@Composable
fun EditEmailScreen(
    navController: NavController,
    viewModel: EditEmailScreenViewModel = hiltViewModel()
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
                is EditEmailScreenViewModel.UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                    )
                }
                is EditEmailScreenViewModel.UiEvent.ChangeStacks -> {
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
                ).background(color = SettingsBG)
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
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(focusRequester),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                localFocusManager.clearFocus()
                            }
                        ),
                        textFieldColors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color.White,
                            focusedLabelColor = Color.White,
                            unfocusedBorderColor = Color.White,
                            unfocusedLabelColor = Color.White,
                            trailingIconColor = Color.White,
                            cursorColor = Color.White,
                            textColor = Color.White
                        ),
                    )
                }
                Button(
                    enabled = !viewModel.isEmailError.value, // TODO - check that password is filled in as well
                    onClick = {
                        viewModel.onEvent(EditEmailScreenEvent.EnteredPassword(""))
                        viewModel.onEvent(EditEmailScreenEvent.ShowPasswordModal(true))
                    },
                    modifier = Modifier.align(Alignment.BottomCenter)
                ) {
                    Text(text = "Change Email")
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
                        viewModel.onEvent(EditEmailScreenEvent.ShowPasswordModal(false))
                    },
                    onDone = { viewModel.onEvent(EditEmailScreenEvent.UpdateEmail(viewModel.emailText.value)) },
                    onTextChange = { viewModel.onEvent(EditEmailScreenEvent.EnteredPassword(it)) },
                    text = viewModel.passwordText.value,
                )
            }

            if (viewModel.isLoading.value) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}
