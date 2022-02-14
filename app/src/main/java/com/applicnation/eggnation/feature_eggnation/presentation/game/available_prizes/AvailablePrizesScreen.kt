package com.applicnation.eggnation.feature_eggnation.presentation.game.available_prizes

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.background)
    ) {
        LazyVerticalGrid(
            cells = GridCells.Fixed(2),
            contentPadding = PaddingValues(),
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 55.dp, start = 45.dp, end = 45.dp),
        ) {
            items(list) { prize ->
                Log.d("qqq", "inside lazyGrid with ${prize}")
                AvaialblePrizeItem(
                    itemData = prize,
                    viewModel = viewModel
                )
            }
//            items(3) { count ->
//                Column(
//                    horizontalAlignment = Alignment.CenterHorizontally,
//                    verticalArrangement = Arrangement.SpaceEvenly,
//                    modifier = Modifier.height(220.dp)
//                ) {
//                    Image(
//                        painter = painterResource(id = R.drawable.egg),
//                        contentDescription = "image",
//
//                        modifier = Modifier
//                            .size(120.dp)
//                            .clickable {
////                            show = true
//                            }
//                    )
//                    Text(text = "name")
//                }
//            }
        }

        Image(
            painter = painterResource(id = R.drawable.store_screen_bg),
            contentDescription = "availablePrizes background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        if (viewModel.showPrizeInfo.value) {
            AvailablePrizeItemInfoCard(
                viewModel = viewModel,
                modifier = Modifier
                    .align(Alignment.Center)
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

    Card(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Transparent)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .height(220.dp)
                .background(color = Color.Transparent)
        ) {
            Image(
                painter = painterResource(id = image),
                contentDescription = "image",

                modifier = Modifier
                    .size(120.dp)
                    .clickable {
                        // TODO - only do below if viewmodel.showinfo is false
                        viewModel.onEvent(
                            AvailablePrizesScreenEvent.SetPrizeInfo(
                                prizeImage = image,
                                prizeTitle = itemData.prizeName,
                                prizeDesc = itemData.prizeDesc
                            )
                        )
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

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Filled.Close,
            contentDescription = "exit",
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 4.dp, top = 4.dp)
                .clickable {
                    viewModel.onEvent(AvailablePrizesScreenEvent.ShowPrizeInfo(false))
                }
        )
        Spacer(modifier = Modifier.height(8.dp))
        Image(
            painter = painterResource(id = viewModel.prizeImageInfo.value),
            contentDescription = "prize Image",
            modifier = Modifier.size(180.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            style = MaterialTheme.typography.h3,
            text = viewModel.prizeTitleInfo.value
        )
        Spacer(modifier = Modifier.height(24.dp))
        LazyColumn() {
            item {
                Text(
                    style = MaterialTheme.typography.body1,
                    text = viewModel.prizeDescInfo.value
                )
            }
        }
    }
}


@ExperimentalFoundationApi
@Preview(showBackground = true)
@Composable
fun AvailablePrizesScreenPreview() {
    var show by remember { mutableStateOf(true) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.background)
    ) {
        LazyVerticalGrid(
            cells = GridCells.Fixed(2),
            contentPadding = PaddingValues(),
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 55.dp, start = 45.dp, end = 45.dp),
        ) {
            items(3) { count ->
                Card() {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier.height(220.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.egg),
                            contentDescription = "image",

                            modifier = Modifier
                                .size(120.dp)
                                .clickable {
                                    show = true
                                }
                        )
                        Text(text = "name")
                    }
                }
            }
        }

        Image(
            painter = painterResource(id = R.drawable.store_screen_bg),
            contentDescription = "availablePrizes background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        if (show) {
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(350.dp)
                    .background(color = Color.Red),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "exit",
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(start = 4.dp, top = 4.dp)
                        .clickable {
                            // TODO
                        }
                )
                Spacer(modifier = Modifier.height(8.dp))
                Image(
                    painter = painterResource(id = R.drawable.egg),
                    contentDescription = "prize Image",
                    modifier = Modifier.size(180.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    style = MaterialTheme.typography.h3,
                    text = "Title"
                )
                Spacer(modifier = Modifier.height(24.dp))
                LazyColumn() {
                    item {
                        Text(
                            style = MaterialTheme.typography.body1,
                            text = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa vvvvvvvvvvveeeeeeeeeeeeeeeeeeeeeeeeeeeerrrrrrrrrrrrrrrrrrrrrrrrrrrryyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy lllllllllllllllllllooooooooooooooooooooonnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnngggggggggggggggggggggggggggggggg ddddddddddddddddddddddddddddeeeeeeeeeeeeescripppppppppppppppppppppppppppppppppppppppttttttttttttttttttttttttttttttttttttttttttttttttttttiiiiiiiiiiiiiiiiiiiiioooooooooooooooooooooooonnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn!!!!!!!!!!!!!!!!"
                        )
                    }
                }
            }
        }
    }
}
