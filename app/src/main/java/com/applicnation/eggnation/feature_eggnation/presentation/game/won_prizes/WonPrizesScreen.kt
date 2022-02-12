package com.applicnation.eggnation.feature_eggnation.presentation.game.won_prizes

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
import com.applicnation.eggnation.feature_eggnation.domain.modal.Prize

@ExperimentalFoundationApi
@Composable
fun WonPrizesScreen(
    navController: NavController,
    viewModel: WonPrizesScreenViewModel = hiltViewModel()
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
                WonPrizeItem(
                    itemData = prize,
                    viewModel = viewModel
                )
            }
        }

        if (viewModel.showPrizeInfo.value) {
            WonPrizeItemInfoCard(
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
private fun WonPrizeItem(
    itemData: Prize,
    viewModel: WonPrizesScreenViewModel
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
                    viewModel.onEvent(
                        WonPrizesScreenEvent.SetPrizeInfo(
                        prizeImage = image,
                        prizeTitle = itemData.prizeName,
                        prizeDesc = itemData.prizeDesc
                    ))
                    viewModel.onEvent(WonPrizesScreenEvent.ShowPrizeInfo(true))
                }
            )
            Text(text = itemData.prizeName)
        }

    }
}

@Composable
fun WonPrizeItemInfoCard(
    modifier: Modifier,
    viewModel: WonPrizesScreenViewModel
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
                // TODO - when the user wants to claim the item, send them to a page where they can input their shipping address
                viewModel.onEvent(WonPrizesScreenEvent.ShowPrizeInfo(false))
            }) {
                // TODO - check if prize was claimed or not and set the text basd on that
                Text(text = "claim")
            }

        }
    }
}

//@ExperimentalFoundationApi
//@Preview(showBackground = true)
//@Composable
//fun WonPrizesScreenPreview() {
//    val list = (1..10).map { it.toString() }
//
//    Box() {
//        LazyVerticalGrid(
//            cells = GridCells.Fixed(2),
//            contentPadding = PaddingValues(),
//            modifier = Modifier.fillMaxSize(),
//        ) {
//            items(list.size) { index ->
//                WonPrizeItem(list[index])
//            }
//        }
//        WonPrizeItemInfoCard(
//            modifier = Modifier
//                .align(Alignment.CenterStart)
//                .background(Color.Red)
//                .width(400.dp)
//                .height(400.dp), // TODO - make width and height based on screen dimensions
//        )
//    }
//}


//@Preview(showBackground = true)
//@Composable
//fun WonPrizeItemInfoCardPreview(
//    modifier: Modifier
//) {
//    Box(
//        modifier = modifier,
//        contentAlignment = Alignment.Center
//    ) {
//        Column(
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Image(
//                painter = painterResource(id = R.drawable.egg),
//                contentDescription = "item image",
//            )
//            Text(text = "Title")
//            Text(text = "Scrollable description")
//
//            Button(onClick = { /*TODO*/ }) {
//                Text(text = "claim")
//            }
//
//        }
//    }
//}




