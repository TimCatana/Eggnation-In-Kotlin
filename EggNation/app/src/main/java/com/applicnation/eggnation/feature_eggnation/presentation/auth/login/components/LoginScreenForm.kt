package com.applicnation.eggnation.feature_eggnation.presentation.auth.login.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.applicnation.eggnation.R
import com.applicnation.eggnation.feature_eggnation.presentation.auth.login.LoginScreenEvent
import com.applicnation.eggnation.feature_eggnation.presentation.auth.login.LoginScreenViewModel
import com.applicnation.eggnation.feature_eggnation.presentation.components.EmailTextField
import com.applicnation.eggnation.feature_eggnation.presentation.components.PasswordTextField
import com.applicnation.eggnation.feature_eggnation.presentation.navigation.AuthScreen
import com.applicnation.eggnation.ui.theme.SpaceMedium
import com.applicnation.eggnation.ui.theme.SpaceSmall

@Composable
fun LoginScreenForm(
    navController: NavController,
    viewModel: LoginScreenViewModel,
    localFocusManager: FocusManager,
    interactionSource: MutableInteractionSource
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(SpaceMedium)
    ) {
        Text(
            text = stringResource(id = R.string.LS_loginScreenTitle),
            style = MaterialTheme.typography.h5
        )
        Spacer(modifier = Modifier.height(SpaceMedium))
        EmailTextField(
            text = viewModel.emailText.value,
            onValueChange = { viewModel.onEvent(LoginScreenEvent.EnteredEmail(it)) },
            isError = viewModel.isEmailError.value,
            imeAction = ImeAction.Next,
            keyboardActions = KeyboardActions(
                onNext = { localFocusManager.moveFocus(FocusDirection.Down) }
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(SpaceMedium))
        PasswordTextField(
            text = viewModel.passwordText.value,
            onValueChange = { viewModel.onEvent(LoginScreenEvent.EnteredPassword(it)) },
            isError = false,
            imeAction = ImeAction.Done,
            keyboardActions = KeyboardActions(
                onDone = { localFocusManager.clearFocus() }
            ),
            isIconClickable = !viewModel.isLoading.value,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(SpaceSmall))
        Text( // TODO - color this text to show that it is clickable
            text = stringResource(id = R.string.LS_forgotPasswordText),
            modifier = Modifier
                .align(Alignment.End)
                .clickable(
                    enabled = !viewModel.isLoading.value,
                    interactionSource = interactionSource,
                    indication = null
                ) {
                    navController.navigate(AuthScreen.ForgotPassword.route)
                }
        )
        Spacer(modifier = Modifier.height(SpaceMedium))
        Button(
            enabled = !viewModel.isLoading.value
                    && !viewModel.isEmailError.value
                    && !viewModel.isPasswordError.value,
            onClick = {
                localFocusManager.clearFocus()
                viewModel.onEvent(LoginScreenEvent.SignIn)
            },
        ) {
            Text(text = stringResource(id = R.string.LS_loginBtnLabel))
        }
    }
}