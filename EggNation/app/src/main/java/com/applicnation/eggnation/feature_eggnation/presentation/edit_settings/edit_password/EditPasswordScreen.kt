package com.applicnation.eggnation.feature_eggnation.presentation.edit_settings.edit_password

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.applicnation.eggnation.R
import com.applicnation.eggnation.feature_eggnation.presentation.components.*
import com.applicnation.eggnation.feature_eggnation.presentation.navigation.GameScreen
import com.applicnation.eggnation.ui.theme.*
import com.applicnation.eggnation.util.WindowInfo
import com.applicnation.eggnation.util.rememberWindowInfo
import kotlinx.coroutines.flow.collectLatest

@Composable
fun EditPasswordScreen(
    navController: NavController,
    viewModel: EditPasswordScreenViewModel = hiltViewModel()

) {
    val windowInfo = rememberWindowInfo()
    val scaffoldState = rememberScaffoldState()
    val localFocusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    val interactionSource = remember { MutableInteractionSource() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is EditPasswordScreenViewModel.UiEvent.ShowSnackBar -> {
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
            modifier = Modifier.fillMaxSize()
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = stringResource(id = R.string.CD_backArrowIcon),
                tint = Color.White,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(start = SpaceSmall, top = SpaceSmall)
                    .clickable(
                        enabled = !viewModel.isLoading.value && !viewModel.isPasswordModelShowing.value,
                        interactionSource = interactionSource,
                        indication = null,
                    ) {
                        navController.popBackStack()
                    }
            )

            if (windowInfo.screenWidthInfo is WindowInfo.WindowType.Compact) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            start = SpaceLarge,
                            end = SpaceLarge,
                            top = SpaceLarge,
                            bottom = SpaceXXl
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    EditPasswordScreenContent(
                        viewModel = viewModel,
                        focusRequester = focusRequester,
                        localFocusManager = localFocusManager,
                    )
                }
            } else {
                Box(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxWidth(0.6f)
                        .padding(bottom = SpaceXXl)
                        .fillMaxHeight()
                ) {
                    EditPasswordScreenContent(
                        viewModel = viewModel,
                        focusRequester = focusRequester,
                        localFocusManager = localFocusManager,
                    )
                }
            }

            if (viewModel.isLoading.value) {
                BackHandler(enabled = true) {}
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}

@Composable
private fun EditPasswordScreenContent(
    viewModel: EditPasswordScreenViewModel,
    localFocusManager: FocusManager,
    focusRequester: FocusRequester
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center)
        ) {
            PasswordTextField(
                text = viewModel.passwordText.value,
                onValueChange = { viewModel.onEvent(EditPasswordScreenEvent.EnteredPassword(it)) },
                isError = viewModel.isPasswordError.value,
                imeAction = ImeAction.Next,
                keyboardActions = KeyboardActions(
                    onNext = { localFocusManager.moveFocus(FocusDirection.Down) }
                ),
                isIconClickable = !viewModel.isLoading.value,
                textFieldColors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Blue,
                    focusedLabelColor = Color.Blue,
                    unfocusedBorderColor = Color.White,
                    unfocusedLabelColor = Color.White,
                    trailingIconColor = Color.White,
                    cursorColor = Color.Blue,
                    textColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester),
            )
            Spacer(modifier = Modifier.height(SpaceMedium))
            ConfirmPasswordTextField(
                text = viewModel.confirmPasswordText.value,
                onValueChange = { viewModel.onEvent(EditPasswordScreenEvent.EnteredConfirmPassword(it)) },
                isError = viewModel.isConfirmPasswordError.value,
                imeAction = ImeAction.Done,
                keyboardActions = KeyboardActions(
                    onDone = { localFocusManager.clearFocus() }
                ),
                isIconClickable = !viewModel.isLoading.value,
                textFieldColors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Blue,
                    focusedLabelColor = Color.Blue,
                    unfocusedBorderColor = Color.White,
                    unfocusedLabelColor = Color.White,
                    trailingIconColor = Color.White,
                    cursorColor = Color.Blue,
                    textColor = Color.White
                ),
                modifier = Modifier.fillMaxWidth(),
            )
        }
        Button(
            enabled = !viewModel.isPasswordError.value
                    && !viewModel.isConfirmPasswordError.value
                    && !viewModel.isPasswordModelShowing.value,
            onClick = {
                viewModel.onEvent(EditPasswordScreenEvent.EnteredValidationPassword(""))
                viewModel.onEvent(EditPasswordScreenEvent.ShowPasswordModel)
            },
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            Text(text = "Change Password")
        }

        if (viewModel.isPasswordModelShowing.value) {
            BackHandler(enabled = true) {
                viewModel.onEvent(EditPasswordScreenEvent.HidePasswordModel)
            }
            PasswordModal(
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth()
                    .height(220.dp),
                onConfirm = { viewModel.onEvent(EditPasswordScreenEvent.UpdatePassword)  },
                onTextChange = { viewModel.onEvent(EditPasswordScreenEvent.EnteredValidationPassword(it)) },
                text = viewModel.validationPasswordText.value,
            )
        }
    }
}