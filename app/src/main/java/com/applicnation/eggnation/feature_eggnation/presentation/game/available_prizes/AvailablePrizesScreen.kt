package com.applicnation.eggnation.feature_eggnation.presentation.game.available_prizes

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun AvailablePrizesScreen() {

}


@ExperimentalFoundationApi
@Preview(showBackground = true)
@Composable
fun AvailablePrizesScreenPreview() {
    val list = (1..10).map { it.toString() }

    LazyVerticalGrid(
        cells = GridCells.Fixed(2),
        contentPadding = PaddingValues(),
        modifier = Modifier.fillMaxSize(),
    ) {
        items(list.size) { index ->
            AvailablePrizeItem(list[index])
        }
    }
}


@Composable
private fun AvailablePrizeItem(itemData: Any) {
    Card(modifier = Modifier.fillMaxSize()) {
        Column() {
            Text(text = itemData.toString())
            Button(onClick = { /*TODO*/ }) {
                Text(text = "claim ${itemData.toString()}")
            }
        }
    }
}