package com.applicnation.eggnation.feature_eggnation.presentation.auth.login.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.applicnation.eggnation.R
import com.applicnation.eggnation.feature_eggnation.presentation.auth.login.LoginScreenViewModel
import com.applicnation.eggnation.feature_eggnation.presentation.navigation.AuthScreen

@Composable
fun LoginScreenBottomText(
    viewModel: LoginScreenViewModel,
    navController: NavController,
    interactionSource: MutableInteractionSource,
    modifier: Modifier
) {
    Row(
        modifier = modifier
    ) {
        Text(text = stringResource(id = R.string.LS_noAccountText))
        Text(
            text = stringResource(id = R.string.LS_signUpText),
            color = Color.Blue,
            modifier = Modifier.clickable(
                enabled = !viewModel.isLoading.value,
                interactionSource = interactionSource,
                indication = null
            ) {
                navController.navigate(AuthScreen.Register.route)
            }
        )
    }
}
