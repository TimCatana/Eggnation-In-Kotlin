package com.applicnation.eggnation.feature_eggnation.presentation.edit_settings.edit_password

import android.graphics.Paint
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.applicnation.eggnation.feature_eggnation.presentation.components.StandardTextField
import com.applicnation.eggnation.feature_eggnation.presentation.edit_settings.edit_username.EditUsernameScreenViewModel

@Composable
fun EditPasswordScreen(
    navController: NavController,
    viewModel: EditPasswordScreenViewModel = hiltViewModel()

) {
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
                        text = viewModel.passwordText.value,
                        onValueChange = {
                                viewModel.onEvent(EditPasswordScreenEvent.EnteredPassword(it))
                        },
                        isError = viewModel.isPasswordError.value,
                        errorText = "Lowercase, uppsercase numbers 8 chars",
                        label = "new password",
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    StandardTextField( // TODO - keep this disabled until the password is equal to the current password
                        text = viewModel.confirmPasswordText.value,
                        onValueChange = {
                            viewModel.onEvent(EditPasswordScreenEvent.EnteredConfirmPassword(it))
                        },
                        isError = viewModel.isConfirmPasswordError.value,
                        errorText = "passwords must match",
                        label = "confirm password",
                    )
                }
                Button(
                    enabled = !viewModel.isPasswordError.value && !viewModel.isConfirmPasswordError.value,
                    onClick = {
                              viewModel.onEvent(EditPasswordScreenEvent.UpdatePassword(viewModel.passwordText.value))
                    },
                    modifier = Modifier.align(Alignment.BottomCenter)
                ) {
                    Text(text = "Change Password")
                }
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
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    StandardTextField(
                        text = "test",
                        onValueChange = {},
                        isError = false,
                        errorText = "Invalid email address",
                        label = "new password",
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