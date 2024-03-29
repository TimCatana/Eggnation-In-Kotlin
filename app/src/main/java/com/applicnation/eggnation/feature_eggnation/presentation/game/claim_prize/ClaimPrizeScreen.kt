package com.applicnation.eggnation.feature_eggnation.presentation.game.claim_prize

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.applicnation.eggnation.feature_eggnation.domain.modal.Country
import com.applicnation.eggnation.feature_eggnation.domain.modal.Region
import com.applicnation.eggnation.feature_eggnation.presentation.game.claim_prize.components.CountryPickerBottomSheet
import com.applicnation.eggnation.feature_eggnation.presentation.game.claim_prize.components.CountryTextField
import com.applicnation.eggnation.feature_eggnation.presentation.game.claim_prize.components.RegionPickerBottomSheet
import com.applicnation.eggnation.feature_eggnation.presentation.game.claim_prize.components.RegionTextField
import com.applicnation.eggnation.feature_eggnation.presentation.game.won_prizes.WonPrizesScreenViewModel
import countryList

@ExperimentalMaterialApi
@Composable
fun ClaimPrizeScreen(
    navController: NavController,
    viewModel: ClaimPrizeScreenViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val countries = remember { countryList(context) }

    LaunchedEffect(key1 = viewModel.selectedCountry.value) {
        viewModel.onEvent(
            ClaimPrizeScreenEvent.GetRegions(
                context = context,
                selectedCountry = viewModel.selectedCountry.value!!
            )
        )
    }



    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CountryPickerBottomSheet(
            countries = countries,
            title = {
                Text(
                    text = "Select Country",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            },
            onItemSelected = {
                viewModel.onEvent(ClaimPrizeScreenEvent.SelectCountry(it))
                viewModel.onEvent(ClaimPrizeScreenEvent.ShowBottomSheet(false, viewModel.isCountryList.value))
            },
            show = viewModel.isShowingBottomSheet.value,
            onDismissRequest = {
                viewModel.onEvent(ClaimPrizeScreenEvent.ShowBottomSheet(false, viewModel.isCountryList.value))
            }
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CountryTextField(
                    label = "Select Country",
                    modifier = Modifier
                        .padding(top = 50.dp),
                    expanded = viewModel.isShowingBottomSheet.value,
                    selectedCountry = viewModel.selectedCountry.value
                ) {
                    viewModel.onEvent(ClaimPrizeScreenEvent.ShowBottomSheet(!viewModel.isShowingBottomSheet.value, true))
                }

                RegionTextField(
                    label = "Select Region",
                    modifier = Modifier
                        .padding(top = 50.dp),
                    expanded = viewModel.isShowingBottomSheet.value,
                    selectedRegion = viewModel.selectedRegion.value
                ) {
                    viewModel.onEvent(ClaimPrizeScreenEvent.ShowBottomSheet(!viewModel.isShowingRegionBottomSheet.value, false))
                }
            }
        }

//        RegionPickerBottomSheet(
//            title = {
//                Text(
//                    text = "Select Region",
//                    textAlign = TextAlign.Center,
//                    fontWeight = FontWeight.Bold,
//                    fontSize = 20.sp,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(16.dp)
//                )
//            },
//            show = viewModel.isShowingRegionBottomSheet.value,
//            availableRegions = viewModel.currentRegions.value,
//            onItemSelected = {
//                viewModel.onEvent(ClaimPrizeScreenEvent.SelectRegion(it))
//                viewModel.onEvent(ClaimPrizeScreenEvent.ShowRegionBottomSheet(false))
//            },
//            onDismissRequest = {
//                viewModel.onEvent(ClaimPrizeScreenEvent.ShowRegionBottomSheet(false))
//            },
//        ) {
//            RegionTextField(
//                label = "Select Region",
//                modifier = Modifier
//                    .padding(top = 50.dp),
//                expanded = viewModel.isShowingRegionBottomSheet.value,
//                selectedRegion = viewModel.selectedRegion.value
//            ) {
//                viewModel.onEvent(ClaimPrizeScreenEvent.ShowRegionBottomSheet(!viewModel.isShowingRegionBottomSheet.value))
//            }
//        }

    }
}

