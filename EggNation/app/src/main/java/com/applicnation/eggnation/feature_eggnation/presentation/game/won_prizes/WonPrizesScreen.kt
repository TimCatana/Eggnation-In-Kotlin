package com.applicnation.eggnation.feature_eggnation.presentation.game.won_prizes

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.applicnation.eggnation.R
import com.applicnation.eggnation.feature_eggnation.domain.modal.WonPrize
import com.applicnation.eggnation.feature_eggnation.presentation.components.PrizeInfoCard
import com.applicnation.eggnation.feature_eggnation.presentation.components.getPrizeImage
import com.applicnation.eggnation.feature_eggnation.presentation.game.available_prizes.AvailablePrizesScreenEvent
import com.applicnation.eggnation.feature_eggnation.presentation.navigation.GameScreen
import com.applicnation.eggnation.ui.theme.StoreBG
import com.applicnation.eggnation.util.WindowInfo
import com.applicnation.eggnation.util.rememberWindowInfo
import kotlinx.coroutines.flow.collectLatest

@ExperimentalFoundationApi
@Composable
fun WonPrizesScreen(
    navController: NavController,
    viewModel: WonPrizesScreenViewModel = hiltViewModel()
) {
    val windowInfo = rememberWindowInfo()

    val list = viewModel.prizes.value
    val interactionSource = remember { MutableInteractionSource() }
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is WonPrizesScreenViewModel.UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                    )
                }
                is WonPrizesScreenViewModel.UiEvent.NavToClaimPrizeScreen -> {
                    navController.navigate(GameScreen.ClaimPrize.passPrizeId(viewModel.prize.value.prizeId))
                }
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
    ) {
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
                    WonPrizeItem(
                        prize = prize,
                        viewModel = viewModel,
                        interactionSource = interactionSource
                    )
                }
            }

            Image(
                painter = painterResource(id = R.drawable.store_screen_bg),
                contentDescription = "wonPrizes background",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.fillMaxSize()
            )


            if (viewModel.showPrizeInfo.value) {
                BackHandler(enabled = true) {
                    viewModel.onEvent(WonPrizesScreenEvent.HidePrizeInfo)
                }
                PrizeInfoCard(
                    prizeTitle = viewModel.prize.value.prizeTitle,
                    prizeDesc = viewModel.prize.value.prizeDesc,
                    prizeType = viewModel.prize.value.prizeType,
                    showClaimButton = true,
                    prizeClaimed = viewModel.prize.value.prizeClaimed,
                    onClaimButtonClick = {
                        viewModel.onEvent(WonPrizesScreenEvent.GoToClaimScreenIfValid)
                    },
                    onDismissCard = { viewModel.onEvent(WonPrizesScreenEvent.HidePrizeInfo) },
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
}

@Composable
private fun WonPrizeItem(
    prize: WonPrize,
    viewModel: WonPrizesScreenViewModel,
    interactionSource: MutableInteractionSource,
) {
    val image = getPrizeImage(prize.prizeType)

    Card() {
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
                            WonPrizesScreenEvent.ShowPrizeInfo(
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