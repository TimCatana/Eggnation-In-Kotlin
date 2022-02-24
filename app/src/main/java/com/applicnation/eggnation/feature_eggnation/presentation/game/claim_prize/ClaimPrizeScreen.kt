package com.applicnation.eggnation.feature_eggnation.presentation.game.claim_prize

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.applicnation.eggnation.feature_eggnation.presentation.auth.register.RegisterScreenEvent
import com.applicnation.eggnation.feature_eggnation.presentation.auth.register.RegisterScreenViewModel
import com.applicnation.eggnation.feature_eggnation.presentation.components.StandardTextField
import com.applicnation.eggnation.feature_eggnation.presentation.navigation.AuthScreen
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

@Composable
fun ClaimPrizeScreen(
    navController: NavController
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
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                StandardTextField(
                    text = "",
                    onValueChange = {},
                    isError = false,
                    errorText = "Username must be at least 5 characters long",
                    label = "username" // use string resources
                )
                Spacer(modifier = Modifier.height(16.dp))
                StandardTextField(
                    text = "",
                    onValueChange = {},
                    isError = false,
                    errorText = "Invalid email address",
                    label = "email" // use string resources
                )
            }
        }
    }
}