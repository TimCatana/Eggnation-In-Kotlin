package com.applicnation.eggnation.feature_eggnation.presentation.auth.register.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.applicnation.eggnation.R
import com.applicnation.eggnation.feature_eggnation.presentation.auth.register.RegisterScreenViewModel
import com.applicnation.eggnation.feature_eggnation.presentation.navigation.PolicyScreen

@Composable
fun RegisterScreenBottomText(
    viewModel: RegisterScreenViewModel,
    navController: NavController,
    interactionSource: MutableInteractionSource,
    modifier: Modifier
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
        ) {
            Text(text = stringResource(id = R.string.RS_agreementText))
        }
        Row(
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)
        ) {
            Text(
                text = stringResource(id = R.string.RS_privacyPolicyText),
                color = Color.Blue,
                modifier = Modifier.clickable(
                    enabled = !viewModel.isLoading.value,
                    interactionSource = interactionSource,
                    indication = null
                ) {
                    navController.navigate(
                        PolicyScreen.PrivacyPolicy.route
                    )
                })
            Text(text = stringResource(id = R.string.RS_andText))
            Text(
                text = stringResource(id = R.string.RS_termsAndConditionsText),
                color = Color.Blue,
                modifier = Modifier.clickable(
                    enabled = !viewModel.isLoading.value,
                    interactionSource = interactionSource,
                    indication = null
                ) {
                    navController.navigate(PolicyScreen.TermsOfService.route)
                }
            )
        }
    }
}
