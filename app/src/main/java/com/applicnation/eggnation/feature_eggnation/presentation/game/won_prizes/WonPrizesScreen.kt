package com.applicnation.eggnation.feature_eggnation.presentation.game.won_prizes

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.applicnation.eggnation.R

@ExperimentalFoundationApi
@Composable
fun WonPrizesScreen(
    navController: NavController,
) {
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

@ExperimentalFoundationApi
@Preview(showBackground = true)
@Composable
fun WonPrizesScreenPreview() {
    val list = (1..10).map { it.toString() }

    Box() {
        LazyVerticalGrid(
            cells = GridCells.Fixed(2),
            contentPadding = PaddingValues(),
            modifier = Modifier.fillMaxSize(),
        ) {
            items(list.size) { index ->
                WonPrizeItem(list[index])
            }
        }
        WonPrizeItemInfoCard(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .background(Color.Red)
                .width(400.dp)
                .height(400.dp), // TODO - make width and height based on screen dimensions
        )
    }
}


@Preview(showBackground = true)
@Composable
fun WonPrizeItemInfoCard(
    @PreviewParameter() modifier: Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.egg),
                contentDescription = "item image",
            )
            Text(text = "Title")
            Text(text = "Scrollable description")

            Button(onClick = { /*TODO*/ }) {
                Text(text = "claim")
            }

        }
    }
}


@Composable
private fun WonPrizeItem(itemData: Any) {
    Card(modifier = Modifier.fillMaxSize()) {
//        Column() {
//            Text(text = itemData.toString())
        Button(onClick = { /*TODO*/ }) {
            Text(text = "claim ${itemData.toString()}")
        }
//        }
    }
}

