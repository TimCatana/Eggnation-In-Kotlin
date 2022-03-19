package com.applicnation.eggnation.feature_eggnation.presentation.game.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.applicnation.eggnation.R
import com.applicnation.eggnation.feature_eggnation.presentation.game.home.HomeScreenViewModel
import com.applicnation.eggnation.feature_eggnation.presentation.navigation.GameScreen

@Composable
fun HomeScreenBottomIcons(
    viewModel: HomeScreenViewModel,
    navController: NavController,
    interactionSource: MutableInteractionSource
) {
    Row() {
        Image(
            painter = painterResource(id = R.drawable.store_icon),
            contentDescription = "store icon",
            Modifier
                .size(60.dp)
                .clickable(
                    enabled = !viewModel.isAnimationPlaying.value,
                    interactionSource = interactionSource,
                    indication = null,
                ) {
                    navController.navigate(GameScreen.AvailablePrizes.route)
                }
        )
        Image(
            painter = painterResource(id = R.drawable.store_icon),
            contentDescription = "store icon",
            Modifier
                .size(60.dp)
                .clickable(
                    enabled = !viewModel.isAnimationPlaying.value,
                    interactionSource = interactionSource,
                    indication = null,
                ) {
                    navController.navigate(GameScreen.WonPrizes.route)
                }
        )
    }
}