package com.applicnation.eggnation.feature_eggnation.presentation.edit_settings.edit_email

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.applicnation.eggnation.feature_eggnation.presentation.auth.forgot_password.ForgotPasswordScreenEvent
import com.applicnation.eggnation.feature_eggnation.presentation.auth.forgot_password.ForgotPasswordScreenViewModel
import com.applicnation.eggnation.feature_eggnation.presentation.components.StandardTextField
import kotlinx.coroutines.flow.collectLatest

@Composable
fun EditEmailScreen(
    navController: NavController,
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
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 40.dp)
            ) {
                StandardTextField(
                    text = "test",
                    onValueChange = {},
                    isError = false,
                    errorText = "Invalid email address",
                    label = "email",
                )
                Spacer(modifier = Modifier.height(16.dp))
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


@Preview(showBackground = true)
@Composable
fun EmailScreenPreview() {
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
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 40.dp)
            ) {
                StandardTextField(
                    text = "test",
                    onValueChange = {},
                    isError = false,
                    errorText = "Invalid email address",
                    label = "email",
                )
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