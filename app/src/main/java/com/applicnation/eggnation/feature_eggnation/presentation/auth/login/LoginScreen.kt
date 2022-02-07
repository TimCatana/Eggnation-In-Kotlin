package com.applicnation.eggnation.feature_eggnation.presentation.auth.login

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.applicnation.eggnation.feature_eggnation.presentation.components.StandardTextField


// TODO - navigate to register screen when "Sign up" text is clicked
@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginScreenViewModel = hiltViewModel()
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
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .align(Alignment.Center),
        ) {
            Text(
                text = "Login Screen",
                style = MaterialTheme.typography.h1
            )
            Spacer(modifier = androidx.compose.ui.Modifier.height(16.dp))
            StandardTextField(
                text = viewModel.emailText.value,
                onValueChange = {
                    viewModel.onEvent(LoginScreenEvent.EnteredEmail(it))
                },
                hint = "email" // use string resources
            )
            Spacer(modifier = androidx.compose.ui.Modifier.height(16.dp))
            StandardTextField(
                text = viewModel.passwordText.value,
                onValueChange = {
                    viewModel.onEvent(LoginScreenEvent.EnteredPassword(it))
                },
                keyboardType = KeyboardType.Password,
                hint = "password" // use string resources
            )
        }
        Text(
            text = buildAnnotatedString {
                append("Don't have an account yet?")
                append(" ")
                // TODO - for string resources, create a val here and put the val in the with styles cause you can't accesss strng rersources from within withStyle
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colors.primary
                    )
                ) {
                    append("Sign up")
                }
            },
            style = MaterialTheme.typography.body1,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}