package com.applicnation.eggnation.feature_eggnation.presentation.auth.forgot_password

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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.applicnation.eggnation.R
import com.applicnation.eggnation.feature_eggnation.presentation.components.EmailTextField
import com.applicnation.eggnation.ui.theme.*
import com.applicnation.eggnation.util.WindowInfo
import com.applicnation.eggnation.util.rememberWindowInfo
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ForgotPasswordScreen(
    navController: NavController,
    viewModel: ForgotPasswordScreenViewModel = hiltViewModel()
) {
    val windowInfo = rememberWindowInfo()
    val scaffoldState = rememberScaffoldState()
    val interactionSource = remember { MutableInteractionSource() }
    val localFocusManager = LocalFocusManager.current

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is ForgotPasswordScreenViewModel.UiEvent.ShowSuccessSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                        duration = SnackbarDuration.Short
                    )
                    navController.popBackStack()
                }
                is ForgotPasswordScreenViewModel.UiEvent.ShowErrorSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                        duration = SnackbarDuration.Short
                    )
                }
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = stringResource(id = R.string.CD_backArrowIcon),
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(start = SpaceSmall, top = SpaceSmall)
                    .clickable(
                        enabled = !viewModel.isLoading.value,
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
                    ForgotPasswordScreenContent(
                        viewModel = viewModel,
                        localFocusManager = localFocusManager,
                    )
                }
            } else {
                Box(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .width(NonCompactWindowAuthBoxWidth)
                        .fillMaxHeight()
                ) {
                    ForgotPasswordScreenContent(
                        viewModel = viewModel,
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
fun ForgotPasswordScreenContent(
    viewModel: ForgotPasswordScreenViewModel,
    localFocusManager: FocusManager,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = SpaceMedium)
    ) {
        Spacer(modifier = Modifier.height(SpaceMedium))
        EmailTextField(
            text = viewModel.emailText.value,
            onValueChange = { viewModel.onEvent(ForgotPasswordScreenEvent.EnteredEmail(it)) },
            isError = viewModel.isEmailError.value,
            imeAction = ImeAction.Done,
            keyboardActions = KeyboardActions(
                onDone = { localFocusManager.clearFocus() }
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(SpaceMedium))
        Button(
            enabled = !viewModel.isEmailError.value,
            onClick = {
                localFocusManager.clearFocus()
                viewModel.onEvent(ForgotPasswordScreenEvent.SendResetPasswordEmail)
            },
        ) {
            Text(text = stringResource(id = R.string.FPS_sendEmailBtnLabel))
        }
    }
}
