package com.applicnation.eggnation.feature_eggnation.presentation.edit_settings.edit_email

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
import com.applicnation.eggnation.feature_eggnation.presentation.components.EmailTextField
import com.applicnation.eggnation.feature_eggnation.presentation.components.PasswordModal
import com.applicnation.eggnation.feature_eggnation.presentation.edit_settings.edit_password.EditPasswordScreenEvent
import com.applicnation.eggnation.feature_eggnation.presentation.navigation.GameScreen
import com.applicnation.eggnation.ui.theme.*
import com.applicnation.eggnation.util.WindowInfo
import com.applicnation.eggnation.util.rememberWindowInfo
import kotlinx.coroutines.flow.collectLatest

@Composable
fun EditEmailScreen(
    navController: NavController,
    viewModel: EditEmailScreenViewModel = hiltViewModel()
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
                is EditEmailScreenViewModel.UiEvent.ShowSnackBar -> {
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
                    EditEmailScreenContent(
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
                    EditEmailScreenContent(
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
private fun EditEmailScreenContent(
    viewModel: EditEmailScreenViewModel,
    focusRequester: FocusRequester,
    localFocusManager: FocusManager,
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        EmailTextField(
            text = viewModel.emailText.value,
            onValueChange = { viewModel.onEvent(EditEmailScreenEvent.EnteredEmail(it)) },
            isError = viewModel.isEmailError.value,
            imeAction = ImeAction.Done,
            keyboardActions = KeyboardActions(
                onDone = { localFocusManager.clearFocus() }
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
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
                .focusRequester(focusRequester),
        )
        Button(
            enabled = !viewModel.isEmailError.value && !viewModel.isPasswordModelShowing.value,
            onClick = {
                viewModel.onEvent(EditEmailScreenEvent.EnteredPassword(""))
                viewModel.onEvent(EditEmailScreenEvent.ShowPasswordModal)
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding()
        ) {
            Text(text = "Change Email")
        }

        if (viewModel.isPasswordModelShowing.value) {
            BackHandler(enabled = true) {
                viewModel.onEvent(EditEmailScreenEvent.HidePasswordModal)
            }
            PasswordModal(
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth()
                    .height(220.dp),
                onConfirm = { viewModel.onEvent(EditEmailScreenEvent.UpdateEmail) },
                onTextChange = { viewModel.onEvent(EditEmailScreenEvent.EnteredPassword(it)) },
                text = viewModel.passwordText.value,
            )
        }
    }
}
