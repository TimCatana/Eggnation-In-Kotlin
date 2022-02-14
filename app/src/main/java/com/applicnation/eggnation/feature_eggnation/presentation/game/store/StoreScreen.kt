package com.applicnation.eggnation.feature_eggnation.presentation.game.store

import android.content.res.Resources
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDirection.Companion.Content
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.applicnation.eggnation.R
import com.applicnation.eggnation.feature_eggnation.domain.modal.SkinsList
import com.applicnation.eggnation.feature_eggnation.presentation.game.available_prizes.AvailablePrizeItemInfoCard
import com.applicnation.eggnation.feature_eggnation.presentation.game.home.HomeScreenViewModel
import com.applicnation.eggnation.feature_eggnation.presentation.game.won_prizes.WonPrizeItemInfoCard

// TODO - make a SkinCard
// TODO - arrange the items correctly
// TODO - maybe add customizations later (hats, scarf, etc...)

@Composable
fun StoreScreen(
    navController: NavController,
    viewModel: StoreScreenViewModel = hiltViewModel()
) {
    val interactionSource = remember { MutableInteractionSource() }


    val skin = SkinsList.skins

    Box(
        modifier = Modifier.fillMaxSize().background(color = MaterialTheme.colors.background),
        contentAlignment = Alignment.Center
    ) {

        LazyRow(
            // TODO - Somehow set the first and last element in the middle
        ) {
            items(skin.size) { index ->
                Image(
                    // TODO - change the size of egg to make it feel natural
                    // TODO - get rid of default tap animation
                    // TODO - set id to the image in the datastore I want to make
                    painter = painterResource(id = skin[index].skinImage),
                    contentDescription = "Tappable Egg",
                    modifier = Modifier.clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) {
                        viewModel.onEvent(StoreScreenEvent.SelectSkin(skin[index].skinImage))
                    }
                )
            }
        }
        Image(
            painter = painterResource(id = R.drawable.store_screen_bg),
            contentDescription = "store background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

    }
}


@Preview(showBackground = true)
@Composable
fun StoreScreenPreview() {
    val skin = SkinsList.skins

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.store_screen_bg),
            contentDescription = "store background",
            modifier = Modifier.fillMaxSize()
        )
        LazyRow {
            items(skin.size) { index ->
                Image(
                    // TODO - change the size of egg to make it feel natural
                    // TODO - get rid of default tap animation
                    // TODO - set id to the image in the datastore I want to make
                    painter = painterResource(id = skin[index].skinImage),
                    contentDescription = "Tappable Egg",
                )
            }
        }
    }
}