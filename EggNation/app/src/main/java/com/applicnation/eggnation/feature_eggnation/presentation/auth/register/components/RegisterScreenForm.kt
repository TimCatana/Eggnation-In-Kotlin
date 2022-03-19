package com.applicnation.eggnation.feature_eggnation.presentation.auth.register.components

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
import com.applicnation.eggnation.R
import com.applicnation.eggnation.feature_eggnation.presentation.auth.register.RegisterScreenEvent
import com.applicnation.eggnation.feature_eggnation.presentation.auth.register.RegisterScreenViewModel
import com.applicnation.eggnation.feature_eggnation.presentation.components.ConfirmPasswordTextField
import com.applicnation.eggnation.feature_eggnation.presentation.components.EmailTextField
import com.applicnation.eggnation.feature_eggnation.presentation.components.PasswordTextField
import com.applicnation.eggnation.ui.theme.SpaceMedium

@Composable
fun RegisterScreenForm(
    viewModel: RegisterScreenViewModel,
    localFocusManager: FocusManager,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(SpaceMedium)
    ) {
        Text(
            text = stringResource(id = R.string.RS_registerScreenTitle),
            style = MaterialTheme.typography.h5
        )
        Spacer(modifier = Modifier.height(SpaceMedium))
        EmailTextField(
            text = viewModel.emailText.value,
            onValueChange = { viewModel.onEvent(RegisterScreenEvent.EnteredEmail(it)) },
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
            onValueChange = { viewModel.onEvent(RegisterScreenEvent.EnteredPassword(it)) },
            isError = viewModel.isPasswordError.value,
            imeAction = ImeAction.Next,
            keyboardActions = KeyboardActions(
                onNext = { localFocusManager.moveFocus(FocusDirection.Down) }
            ),
            isIconClickable = !viewModel.isLoading.value,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(SpaceMedium))
        ConfirmPasswordTextField(
            text = viewModel.confirmPasswordText.value,
            onValueChange = { viewModel.onEvent(RegisterScreenEvent.EnteredConfirmPassword(it)) },
            isError = viewModel.isConfirmPasswordError.value,
            imeAction = ImeAction.Done,
            keyboardActions = KeyboardActions(
                onDone = { localFocusManager.clearFocus() }
            ),
            isIconClickable = !viewModel.isLoading.value,
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(SpaceMedium))
        Button(
            enabled = !viewModel.isEmailError.value
                    && !viewModel.isPasswordError.value
                    && !viewModel.isConfirmPasswordError.value,
            onClick = {
                localFocusManager.clearFocus()
                viewModel.onEvent(RegisterScreenEvent.SignUp)
            },
        ) {
            Text(text = stringResource(id = R.string.RS_registerBtnLabel))
        }
    }
}
