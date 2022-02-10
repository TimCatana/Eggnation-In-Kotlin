package com.applicnation.eggnation.feature_eggnation.presentation.game.store

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.applicnation.eggnation.R
import com.applicnation.eggnation.feature_eggnation.domain.modal.SkinsList
import com.applicnation.eggnation.feature_eggnation.presentation.game.home.HomeScreenViewModel

// TODO - make a SkinCard
// TODO - arrange the items correctly
// TODO - maybe add customizations later (hats, scarf, etc...)

@Composable
fun StoreScreen(
    navController: NavController,
    viewModel: StoreScreenViewModel = hiltViewModel()
) {
    val skin = SkinsList.skins

    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        LazyRow{
            items(skin.size){ index ->
                Image(
                    // TODO - change the size of egg to make it feel natural
                    // TODO - get rid of default tap animation
                    // TODO - set id to the image in the datastore I want to make
                    painter = painterResource(id = skin[index].skinImage),
                    contentDescription = "Tappable Egg",
                    modifier = Modifier.clickable {
                        viewModel.onEvent(StoreScreenEvent.SelectSkin(skin[index].skinImage))
                    }
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun StoreScreenPreview() {
val skin = SkinsList.skins

    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
        ) {
        LazyRow{
            items(skin.size){ index ->
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