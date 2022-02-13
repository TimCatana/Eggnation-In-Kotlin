package com.applicnation.eggnation.feature_eggnation.presentation.game.available_prizes

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.applicnation.eggnation.R
import com.applicnation.eggnation.feature_eggnation.domain.modal.AvailablePrize

@ExperimentalFoundationApi
@Composable
fun AvailablePrizesScreen(
    navController: NavController,
    viewModel: AvailablePrizesScreenViewModel = hiltViewModel()
) {
    val list = viewModel.prizes.value

    Box() {
        LazyVerticalGrid(
            cells = GridCells.Fixed(2),
            contentPadding = PaddingValues(),
            modifier = Modifier.fillMaxSize(),
        ) {
            items(list) { prize ->
                Log.d("qqq", "inside lazyGrid with ${prize}")
                AvaialblePrizeItem(
                    itemData = prize,
                    viewModel = viewModel
                )
            }
        }

        if (viewModel.showPrizeInfo.value) {
            AvailablePrizeItemInfoCard(
                viewModel = viewModel,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .background(Color.Red)
                    .width(400.dp)
                    .height(400.dp), // TODO - make width and height based on screen dimensions
            )
        }

    }
}

@Composable
private fun AvaialblePrizeItem(
    itemData: AvailablePrize,
    viewModel: AvailablePrizesScreenViewModel
) {
    Log.d("qqq", "inside availablePrizesItem with ${itemData}")
    var image: Int

    when (itemData.prizeType) {
        "phone" -> {
            image = R.drawable.egg
        }
        "laptop" -> {
            image = R.drawable.egg_four
        }
        else -> {
            image = R.drawable.egg_three
        }
    }


    Card(modifier = Modifier.fillMaxSize()) { // TODO - make the card clickable rather than the image below
        Column() {
            Image(
                painter = painterResource(id = image),
                contentDescription = "image",
                modifier = Modifier.clickable {
                    // TODO - only do below if viewmodel.showinfo is false
                    viewModel.onEvent(AvailablePrizesScreenEvent.SetPrizeInfo(
                        prizeImage = image,
                        prizeTitle = itemData.prizeName,
                        prizeDesc = itemData.prizeDesc
                    ))
                    viewModel.onEvent(AvailablePrizesScreenEvent.ShowPrizeInfo(true))
                }
            )
            Text(text = itemData.prizeName)
        }

    }
}

@Composable
fun AvailablePrizeItemInfoCard(
    modifier: Modifier,
    viewModel: AvailablePrizesScreenViewModel
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = viewModel.prizeImageInfo.value),
                contentDescription = "item image",
            )
            Text(text = viewModel.prizeTitleInfo.value)
            Text(text = viewModel.prizeDescInfo.value)
            Button(onClick = {
                viewModel.onEvent(AvailablePrizesScreenEvent.ShowPrizeInfo(false))
            }) {
                Text(text = "dismiss")
            }
        }
    }
}
//
//
//@ExperimentalFoundationApi
//@Preview(showBackground = true)
//@Composable
//fun AvailablePrizesScreenPreview() {
//    val list = (1..10).map { it.toString() }
//
//    LazyVerticalGrid(
//        cells = GridCells.Fixed(2),
//        contentPadding = PaddingValues(),
//        modifier = Modifier.fillMaxSize(),
//    ) {
//        items(list.size) { index ->
//            AvailablePrizeItem(list[index])
//        }
//    }
//}
//
//
//@Composable
//private fun AvailablePrizeItem(itemData: Any) {
//    Card(modifier = Modifier.fillMaxSize()) {
//        Column() {
//            Text(text = itemData.toString())
//            Button(onClick = { /*TODO*/ }) {
//                Text(text = "claim ${itemData.toString()}")
//            }
//        }
//    }
//}