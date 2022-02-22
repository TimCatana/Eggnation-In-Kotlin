package com.applicnation.eggnation.feature_eggnation.presentation.game.home

import android.content.Context
import android.content.ContextWrapper
import android.util.Log
import android.widget.Toast
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
    adMob: AdMob,
    viewModel: HomeScreenViewModel = hiltViewModel()
) {
    val interactionSource = remember { MutableInteractionSource() }

    val ctx = LocalContext.current

    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is HomeScreenViewModel.UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                    )
                }
                is HomeScreenViewModel.UiEvent.PlayLoseAnimation -> {}
                is HomeScreenViewModel.UiEvent.PlayWinAnimation -> {}
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
                contentDescription = "exit",
                modifier = Modifier
                    .padding(start = 4.dp, top = 4.dp)
                    .clickable {
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
                Image(
                    // TODO - change the size of egg to make it feel natural
                    // TODO - get rid of default tap animation
                    // TODO - set id to the image in the datastore I want to make
                    painter = painterResource(id = viewModel.eggSkin.value),
                    contentDescription = "Tappable Egg",
                    modifier = Modifier
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null
                        ) {
                            viewModel.onEvent(HomeScreenEvent.DecrementCounter)
                            viewModel.onEvent(HomeScreenEvent.IncrementGlobalCounter)

                            if (viewModel.tapCounter.value % 5 == 0) {
                                viewModel.onEvent(HomeScreenEvent.PlayAd(adMob))
                                // TODO - play lost animation, user get's no chance to win when ad is played. sry bro
                            } else {
                                viewModel.onEvent(HomeScreenEvent.LoadAd(adMob))
                                viewModel.onEvent(HomeScreenEvent.MainGameLogic)
                            }
                            // TODO - add correct egg animation based on whether user won or not
                        }
                )
                Row() {
                    Image(
                        painter = painterResource(id = R.drawable.store_icon),
                        contentDescription = "store icon",
                        Modifier
                            .size(60.dp)
                            .clickable {
                                navController.navigate(GameScreen.AvailablePrizes.route)
                            }
                    )
                    Image(
                        painter = painterResource(id = R.drawable.store_icon),
                        contentDescription = "store icon",
                        Modifier
                            .size(60.dp)
                            .clickable {
                                navController.navigate(GameScreen.Store.route)
                            }
                    )
                    Image(
                        painter = painterResource(id = R.drawable.store_icon),
                        contentDescription = "store icon",
                        Modifier
                            .size(60.dp)
                            .clickable {
                                navController.navigate(GameScreen.WonPrizes.route)
                            }
                    )
                }
            }

            if(viewModel.showWonPrize.value) {
                WonPrizeInfoCard(  viewModel = viewModel,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .background(Color.Red)
                        .width(400.dp)
                        .height(400.dp),)
            }
        }
    }
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

