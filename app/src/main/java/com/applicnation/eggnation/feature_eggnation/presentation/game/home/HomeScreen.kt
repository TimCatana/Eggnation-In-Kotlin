package com.applicnation.eggnation.feature_eggnation.presentation.game.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.applicnation.eggnation.R

@Composable
fun HomeScreen(
    navController: NavController
) {
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
            // TODO - change the size of egg to make it feel natural
            // TODO - get rid of default tap animation
            painter = painterResource(id = R.drawable.egg),
            contentDescription = "Tappable Egg",
            modifier = Modifier.clickable {
                // TODO - add main game prize winning logic here
                // TODO - decrement counter here
                // TODO - add correct egg animation based on whether user won or not
            }
        )
    }
}


@Preview(showBackground = true)
@Composable
fun HomePreview() {
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
                // TODO - add main game prize winning logic here
                // TODO - decrement counter here
            }
        )
    }
}