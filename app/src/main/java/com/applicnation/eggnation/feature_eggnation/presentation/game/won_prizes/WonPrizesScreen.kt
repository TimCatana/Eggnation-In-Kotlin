package com.applicnation.eggnation.feature_eggnation.presentation.game.won_prizes

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController

@Composable
fun WonPrizesScreen(
    navController: NavController,
) {

}

@ExperimentalFoundationApi
@Preview(showBackground = true)
@Composable
fun WonPrizesScreenPreview() {
    val list = (1..10).map { it.toString() }

    LazyVerticalGrid(
        cells = GridCells.Fixed(2),
        contentPadding = PaddingValues(),
        modifier = Modifier.fillMaxSize(),
    ) {
        items(list.size) { index ->
            WonPrizeItem(list[index])
        }
    }
}


@Composable
private fun WonPrizeItem(itemData: Any) {
    Card(modifier = Modifier.fillMaxSize()) {
        Column() {
            Text(text = itemData.toString())
            Button(onClick = { /*TODO*/ }) {
                Text(text = "claim ${itemData.toString()}")
            }
        }
    }
}