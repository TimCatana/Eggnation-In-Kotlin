package com.applicnation.eggnation.feature_eggnation.presentation.auth.register

import androidx.activity.compose.BackHandler
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.applicnation.eggnation.feature_eggnation.presentation.auth.register.components.RegisterScreenBottomText
import com.applicnation.eggnation.feature_eggnation.presentation.auth.register.components.RegisterScreenForm
import com.applicnation.eggnation.ui.theme.NonCompactWindowAuthBoxWidth
import com.applicnation.eggnation.ui.theme.SpaceLarge
import com.applicnation.eggnation.ui.theme.SpaceXXl
import com.applicnation.eggnation.util.WindowInfo
import com.applicnation.eggnation.util.rememberWindowInfo
import kotlinx.coroutines.flow.collectLatest

@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: RegisterScreenViewModel = hiltViewModel()
) {
    val windowInfo = rememberWindowInfo()
    val scaffoldState = rememberScaffoldState()
    val interactionSource = remember { MutableInteractionSource() }
    val localFocusManager = LocalFocusManager.current

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is RegisterScreenViewModel.UiEvent.ShowSnackBar -> {
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
            modifier = Modifier.fillMaxSize()
        ) {
            // TODO - add background image here
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
                    RegisterScreenContent(
                        viewModel = viewModel,
                        navController = navController,
                        localFocusManager = localFocusManager,
                        interactionSource = interactionSource,
                        modifier = Modifier.align(Alignment.BottomCenter)
                    )
                }
            } else {
                Box(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .width(NonCompactWindowAuthBoxWidth)
                        .padding(bottom = SpaceXXl)
                        .fillMaxHeight()
                ) {
                    RegisterScreenContent(
                        viewModel = viewModel,
                        navController = navController,
                        localFocusManager = localFocusManager,
                        interactionSource = interactionSource,
                        modifier = Modifier.align(Alignment.BottomCenter)
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

// TODO - add back arrow
@Composable
fun RegisterScreenContent(
    viewModel: RegisterScreenViewModel,
    navController: NavController,
    localFocusManager: FocusManager,
    interactionSource: MutableInteractionSource,
    modifier: Modifier // For bottom text column
) {
    RegisterScreenForm(
        viewModel = viewModel,
        localFocusManager = localFocusManager,
    )
    RegisterScreenBottomText(
        viewModel = viewModel,
        navController = navController,
        interactionSource = interactionSource,
        modifier = modifier
    )
}
