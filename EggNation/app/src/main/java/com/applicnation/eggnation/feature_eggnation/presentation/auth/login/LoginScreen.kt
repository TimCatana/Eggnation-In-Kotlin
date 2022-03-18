package com.applicnation.eggnation.feature_eggnation.presentation.auth.login

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.applicnation.eggnation.feature_eggnation.presentation.auth.login.components.LoginScreenBottomText
import com.applicnation.eggnation.feature_eggnation.presentation.auth.login.components.LoginScreenForm
import com.applicnation.eggnation.util.WindowInfo
import com.applicnation.eggnation.util.rememberWindowInfo
import kotlinx.coroutines.flow.collectLatest

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginScreenViewModel = hiltViewModel()
) {
    val windowInfo = rememberWindowInfo()
    val interactionSource = remember { MutableInteractionSource() }
    val localFocusManager = LocalFocusManager.current
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is LoginScreenViewModel.UiEvent.ShowSnackbar -> {
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
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(text = windowInfo.screenWidthInfo.toString())

            if (windowInfo.screenWidthInfo is WindowInfo.WindowType.Compact) {
                LoginScreenContent(
                    viewModel = viewModel,
                    navController = navController,
                    localFocusManager = localFocusManager,
                    interactionSource = interactionSource,
                    modifier = Modifier.align(Alignment.BottomCenter)
                )
            } else {
                Box(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .width(400.dp)
                        .fillMaxHeight()
                ) {
                    LoginScreenContent(
                        viewModel = viewModel,
                        navController = navController,
                        localFocusManager = localFocusManager,
                        interactionSource = interactionSource,
                        modifier = Modifier.align(Alignment.BottomCenter)
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
fun LoginScreenContent(
    viewModel: LoginScreenViewModel,
    navController: NavController,
    localFocusManager: FocusManager,
    interactionSource: MutableInteractionSource,
    modifier: Modifier, // For bottom text
) {
    LoginScreenForm(
        navController = navController,
        viewModel = viewModel,
        localFocusManager = localFocusManager,
        interactionSource = interactionSource
    )
    LoginScreenBottomText(
        modifier = modifier,
        interactionSource = interactionSource,
        navController = navController
    )
}
