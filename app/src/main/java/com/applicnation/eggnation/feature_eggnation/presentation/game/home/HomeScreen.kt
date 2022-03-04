package com.applicnation.eggnation.feature_eggnation.presentation.game.home

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.compose.*
import com.applicnation.eggnation.R
import com.applicnation.eggnation.feature_eggnation.data.remote.firebase.AdMob
import com.applicnation.eggnation.feature_eggnation.presentation.auth.forgot_password.ForgotPasswordScreenViewModel
import com.applicnation.eggnation.feature_eggnation.presentation.auth.register.RegisterScreenViewModel
import com.applicnation.eggnation.feature_eggnation.presentation.game.available_prizes.AvailablePrizesScreenEvent
import com.applicnation.eggnation.feature_eggnation.presentation.game.available_prizes.AvailablePrizesScreenViewModel
import com.applicnation.eggnation.feature_eggnation.presentation.navigation.GameScreen
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.interstitial.InterstitialAd
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeScreenViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val interactionSource = remember { MutableInteractionSource() }
    val ctx = LocalContext.current

    val winAnimatable = rememberLottieAnimatable()
    val winComposition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.winner)) // TODO - probably move thi to assets?

    val loseAnimatable = rememberLottieAnimatable()
    val loseComposition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.lost)) // TODO - probably move thi to assets?





    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is HomeScreenViewModel.UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                    )
                }
                is HomeScreenViewModel.UiEvent.PlayLoseAnimation -> {
                    viewModel.onEvent(
                        HomeScreenEvent.SetAnimationPlaying(true)
                    )
                    loseAnimatable.animate(
                        loseComposition,
                        continueFromPreviousAnimate = false,
                    )
                    if (loseAnimatable.isAtEnd) {
                        loseAnimatable.snapTo(progress = 0f)
                        viewModel.onEvent(
                            HomeScreenEvent.SetAnimationPlaying(false)
                        )
                    }
                }
                is HomeScreenViewModel.UiEvent.PlayWinAnimation -> {
                    viewModel.onEvent(
                        HomeScreenEvent.ShowWonAnimation(isShowing = true)
                    )
                    viewModel.onEvent(
                        HomeScreenEvent.SetAnimationPlaying(true)
                    )
                    winAnimatable.animate(
                        winComposition,
                        continueFromPreviousAnimate = false,
                    )
                    if (winAnimatable.isAtEnd) {
                        viewModel.onEvent(
                            HomeScreenEvent.ShowWonAnimation(isShowing = false)
                        )
                        viewModel.onEvent(
                            HomeScreenEvent.SetAnimationPlaying(false)
                        )
                    }
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
        ) {
            Icon(
                imageVector = Icons.Filled.Settings,
                contentDescription = "settings icon",
                modifier = Modifier
                    .padding(start = 4.dp, top = 4.dp)
                    .clickable(
                        enabled = !viewModel.isAnimationPlaying.value,
                        interactionSource = interactionSource,
                        indication = null,
                    ) {
                        navController.navigate(GameScreen.Settings.route)
                    }
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = viewModel.tapCounter.value.toString(),
                    style = MaterialTheme.typography.h3,
                )


                if (viewModel.showLoseAnimation.value) {
                    LottieAnimation(
                        loseComposition,
                        loseAnimatable.progress,
                        modifier = Modifier
                            .width(200.dp)
                            .height(200.dp)
                            .clickable(
                                interactionSource = interactionSource,
                                indication = null,
                                enabled = !viewModel.isAnimationPlaying.value
                            ) {
                                viewModel.onEvent(HomeScreenEvent.DecrementCounter)
                                viewModel.onEvent(HomeScreenEvent.IncrementGlobalCounter)

                                if (viewModel.tapCounter.value % 5 == 0) {
                                    viewModel.onEvent(HomeScreenEvent.PlayAd(ctx.getActivity()))
                                } else {
                                    viewModel.onEvent(HomeScreenEvent.LoadAd(ctx.getActivity()))
                                    viewModel.onEvent(HomeScreenEvent.MainGameLogic)
                                }
                            }
                    )
                } else if (viewModel.showWinAnimation.value) {
                    LottieAnimation(
                        winComposition,
                        winAnimatable.progress,
                        modifier = Modifier
                            .width(200.dp)
                            .height(200.dp)
                    )
                }
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
                                navController.navigate(GameScreen.Store.route)
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

            if (viewModel.showWonPrize.value) {
                WonPrizeInfoCard(
                    viewModel = viewModel,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .background(Color.Red)
                        .width(400.dp)
                        .height(400.dp),
                )
            }
        }
    }
}

/**
 * Get the activity
 */
fun Context.getActivity(): Activity? = when (this) {
    is ComponentActivity -> this
    is ContextWrapper -> baseContext.getActivity() as Activity
    else -> null
}

@Composable
fun WonPrizeInfoCard(
    modifier: Modifier,
    viewModel: HomeScreenViewModel
) {

    val image = when (viewModel.prizeTypeInfo.value) {
        "phone" -> {
            R.drawable.egg
        }
        "laptop" -> {
            R.drawable.egg_four
        }
        else -> {
            R.drawable.egg_three
        }
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Filled.Close,
            contentDescription = "exit",
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 4.dp, top = 4.dp)
                .clickable {
                    viewModel.onEvent(HomeScreenEvent.DismissWonPrizeCard)
                }
        )
        Spacer(modifier = Modifier.height(8.dp))
        Image(
            painter = painterResource(id = image),
            contentDescription = "prize Image",
            modifier = Modifier.size(180.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            style = MaterialTheme.typography.h3,
            text = viewModel.prizeTitleInfo.value
        )
        Spacer(modifier = Modifier.height(24.dp))
        LazyColumn {
            item {
                Text(
                    style = MaterialTheme.typography.body1,
                    text = viewModel.prizeDescInfo.value
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun HomePreview() {
    Icon(
        imageVector = Icons.Filled.Settings,
        contentDescription = "exit",
        modifier = Modifier
            .padding(start = 4.dp, top = 4.dp)
            .clickable {
                // TODO
            }
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "1000",
            style = MaterialTheme.typography.h3
        )
        Image(
            painter = painterResource(id = R.drawable.egg),
            contentDescription = "Tappable Egg",
            modifier = Modifier.clickable {
            }
        )
        Row() {
            Image(
                painter = painterResource(id = R.drawable.store_icon),
                contentDescription = "store icon",
                Modifier
                    .size(60.dp)
                    .clickable {
                    }
            )
            Image(
                painter = painterResource(id = R.drawable.store_icon),
                contentDescription = "store icon",
                Modifier
                    .size(60.dp)
                    .clickable {
                    }
            )
            Image(
                painter = painterResource(id = R.drawable.store_icon),
                contentDescription = "store icon",
                Modifier
                    .size(60.dp)
                    .clickable {
                    }
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun Test() {
    Box(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.padding(10.dp))
    }


}

