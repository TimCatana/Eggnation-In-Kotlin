package com.applicnation.eggnation.feature_eggnation.presentation.auth.forgot_password

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.applicnation.eggnation.feature_eggnation.presentation.components.StandardTextField
import com.applicnation.eggnation.ui.theme.EggNationTheme

@Composable
fun ForgotPasswordScreen(
    navController: NavController,
    viewModel: ForgotPasswordScreenViewModel = hiltViewModel()
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 40.dp)
    ) {
        Text(
            text = "Forgot Password Screen",
            style = MaterialTheme.typography.h5
        )
        Spacer(modifier = Modifier.height(16.dp))
        StandardTextField(
            text = viewModel.emailText.value,
            onValueChange = {
                viewModel.onEvent(ForgotPasswordScreenEvent.EnteredEmail(it))
            },
            isError = viewModel.isEmailError.value,
            errorText = "Invalid email address",
            label = "email" // use string resources
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            enabled = !viewModel.isEmailError.value,
            onClick = {
                viewModel.onEvent(
                    ForgotPasswordScreenEvent.SendResetPasswordEmail(
                        viewModel.emailText.value
                    )
                )
                // TODO - send forgot password email with Firebase Authentication
            },
        ) {
            Text(text = "send email")
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun forgotPasswordPreview() {
    EggNationTheme {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 40.dp)
        ) {
            Text(
                text = "Forgot Password Screen",
                style = MaterialTheme.typography.h5
            )
            Spacer(modifier = Modifier.height(16.dp))
            StandardTextField(
                text = "test",
                onValueChange = {
                },
                label = "email" // use string resources
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { /*TODO*/ },
            ) {
                Text(text = "send email")
            }
        }
    }
}