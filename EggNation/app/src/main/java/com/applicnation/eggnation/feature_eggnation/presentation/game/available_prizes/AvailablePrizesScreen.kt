package com.applicnation.eggnation.feature_eggnation.presentation.game.available_prizes

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.applicnation.eggnation.R
import com.applicnation.eggnation.feature_eggnation.domain.modal.AvailablePrize
import com.applicnation.eggnation.feature_eggnation.presentation.components.PasswordModal
import com.applicnation.eggnation.feature_eggnation.presentation.components.PrizeInfoCard
import com.applicnation.eggnation.feature_eggnation.presentation.components.getPrizeImage
import com.applicnation.eggnation.feature_eggnation.presentation.edit_settings.edit_email.EditEmailScreenEvent
import com.applicnation.eggnation.ui.theme.StoreBG
import com.applicnation.eggnation.util.WindowInfo
import com.applicnation.eggnation.util.rememberWindowInfo

@ExperimentalFoundationApi
@Composable
fun AvailablePrizesScreen(
    navController: NavController,
    viewModel: AvailablePrizesScreenViewModel = hiltViewModel()
) {
    val windowInfo = rememberWindowInfo()
    val interactionSource = remember { MutableInteractionSource() }


    val list = viewModel.prizes.value

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = StoreBG)
    ) {

        LazyVerticalGrid(
            cells = GridCells.Fixed(2),
            contentPadding = PaddingValues(),
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 55.dp, start = 45.dp, end = 45.dp),
        ) {
            items(list) { prize ->
                AvaialblePrizeItem(
                    prize = prize,
                    viewModel = viewModel,
                    interactionSource = interactionSource
                )
            }
        }

        Image(
            painter = painterResource(id = R.drawable.store_screen_bg),
            contentDescription = "availablePrizes background",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )

        if (viewModel.showPrizeInfo.value) {
            BackHandler(enabled = true) {
                viewModel.onEvent(AvailablePrizesScreenEvent.HidePrizeInfo)
            }
            PrizeInfoCard(
                prizeTitle = viewModel.prize.value.prizeTitle,
                prizeDesc = viewModel.prize.value.prizeDesc,
                prizeType = viewModel.prize.value.prizeType,
                showClaimButton = false,
                prizeClaimed = true,
                onClaimButtonClick = {},
                onDismissCard = { viewModel.onEvent(AvailablePrizesScreenEvent.HidePrizeInfo) },
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth(if (windowInfo.screenWidthInfo is WindowInfo.WindowType.Compact) 0.95f else 0.8f)
                    .fillMaxHeight(0.5f)
            )
        }

        if (viewModel.isLoading.value) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }

    }
}

@Composable
private fun AvaialblePrizeItem(
    prize: AvailablePrize,
    viewModel: AvailablePrizesScreenViewModel,
    interactionSource: MutableInteractionSource,
) {
    val image = getPrizeImage(prize.prizeType)

    Card(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .height(220.dp)
                .background(color = StoreBG)
        ) {
            Image(
                painter = painterResource(id = image),
                contentDescription = "image",

                modifier = Modifier
                    .size(120.dp)
                    .clickable(
                        enabled = !viewModel.showPrizeInfo.value,
                        interactionSource = interactionSource,
                        indication = null
                    ) {
                        viewModel.onEvent(
                            AvailablePrizesScreenEvent.ShowPrizeInfo(
                                prize = prize,
                                prizeImage = image,
                            )
                        )
                    }
            )
            Text(text = prize.prizeTitle)
        }
    }
}