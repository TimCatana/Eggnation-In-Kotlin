package com.applicnation.eggnation.feature_eggnation.presentation.game.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
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
    interactionSource: MutableInteractionSource,
    modifier: Modifier
) {
    Row(
        modifier = modifier
    ) {
        Image(
            painter = painterResource(id = R.drawable.store_icon),
            contentDescription = "store icon",
            modifier = Modifier
                .weight(0.5f)
                .fillMaxSize()
                .clickable(
                    enabled = !viewModel.isAnimationPlaying.value && !viewModel.showWonPrize.value,
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
                .weight(0.5f)
                .fillMaxSize()
                .clickable(
                    enabled = !viewModel.isAnimationPlaying.value && !viewModel.showWonPrize.value,
                    interactionSource = interactionSource,
                    indication = null,
                ) {
                    navController.navigate(GameScreen.WonPrizes.route)
                }
        )
    }
}