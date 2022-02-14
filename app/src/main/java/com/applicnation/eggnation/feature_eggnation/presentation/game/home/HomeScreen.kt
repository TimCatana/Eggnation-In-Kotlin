package com.applicnation.eggnation.feature_eggnation.presentation.game.home

import android.content.Context
import android.content.ContextWrapper
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.applicnation.eggnation.R
import com.applicnation.eggnation.feature_eggnation.data.remote.firebase.AdMob
import com.applicnation.eggnation.feature_eggnation.presentation.auth.forgot_password.ForgotPasswordScreenViewModel
import com.applicnation.eggnation.feature_eggnation.presentation.navigation.GameScreen
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.interstitial.InterstitialAd

@Composable
fun HomeScreen(
    navController: NavController,
    adMob: AdMob,
    viewModel: HomeScreenViewModel = hiltViewModel()
) {
    val interactionSource = remember { MutableInteractionSource() }

    val ctx = LocalContext.current

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

                        // TODO - need to wait for above before I can do below

                        if (viewModel.userWon.value) {
                            Toast.makeText(ctx, "You Won!", Toast.LENGTH_SHORT).show()
                            // TODO - prize from realtime
                            // TODO - add prize to firestore into users account document (using userID)
                            // TODO - play won animation and show the prize on screen
                        } else {
                            Toast.makeText(ctx, "You lost!", Toast.LENGTH_SHORT).show()
                            // TODO - play lost animation
                        }
                    }


                    // TODO - add correct egg animation based on whether user won or not
                }
        )
        Row() {
            Image(
                painter = painterResource(id = R.drawable.store_icon),
                contentDescription = "store icon",
                Modifier.size(60.dp).clickable {
                    navController.navigate(GameScreen.AvailablePrizes.route)
                }
            )
            Image(
                painter = painterResource(id = R.drawable.store_icon),
                contentDescription = "store icon",
                Modifier.size(60.dp).clickable {
                    navController.navigate(GameScreen.Store.route)
                }
            )
            Image(
                painter = painterResource(id = R.drawable.store_icon),
                contentDescription = "store icon",
                Modifier.size(60.dp).clickable {
                    navController.navigate(GameScreen.WonPrizes.route)
                }
            )
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
                Modifier.size(60.dp).clickable {
                }
            )
            Image(
                painter = painterResource(id = R.drawable.store_icon),
                contentDescription = "store icon",
                Modifier.size(60.dp).clickable {
                }
            )
            Image(
                painter = painterResource(id = R.drawable.store_icon),
                contentDescription = "store icon",
                Modifier.size(60.dp).clickable {
                }
            )
        }
    }
}


