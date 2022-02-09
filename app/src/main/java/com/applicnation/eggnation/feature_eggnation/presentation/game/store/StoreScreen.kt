package com.applicnation.eggnation.feature_eggnation.presentation.game.store

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.applicnation.eggnation.R

// TODO - make a SkinCard
// TODO - arrange the items correctly
// TODO - maybe add customizations later (hats, scarf, etc...)

@Composable
fun StoreScreen(
    navController: NavController,
) {

}


@Preview(showBackground = true)
@Composable
fun StoreScreenPreview() {
    val list = (1..500).map { it.toString() }

    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
        ) {
        LazyRow{
            items(list.size){ index -> 
                Text(text = list[index])
            }
        }
    }
}