package com.applicnation.eggnation.feature_eggnation.presentation.game.claim_prize

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.applicnation.eggnation.feature_eggnation.domain.modal.Country
import com.applicnation.eggnation.feature_eggnation.domain.modal.Region
import com.applicnation.eggnation.feature_eggnation.presentation.components.StandardTextField
import com.applicnation.eggnation.feature_eggnation.presentation.game.claim_prize.components.CountryPickerBottomSheet
import com.applicnation.eggnation.feature_eggnation.presentation.game.claim_prize.components.CountryTextField
import com.applicnation.eggnation.feature_eggnation.presentation.game.claim_prize.components.RegionPickerBottomSheet
import com.applicnation.eggnation.feature_eggnation.presentation.game.claim_prize.components.RegionTextField
import com.applicnation.eggnation.feature_eggnation.presentation.game.won_prizes.WonPrizesScreenViewModel
import countryList
import timber.log.Timber

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
            titleText = "Select Country", // TODO change based on whether region or country is shown
            show = viewModel.isShowingBottomSheet.value,
            isCountryList = viewModel.isCountryList.value,
            modifier = Modifier.align(Alignment.Center),
            onDismissRequest = { viewModel.onEvent(ClaimPrizeScreenEvent.DismissBottomSheet) },

            countries = countries,
            onCountrySelected = {
                viewModel.onEvent(ClaimPrizeScreenEvent.SelectCountry(it))
                viewModel.onEvent(ClaimPrizeScreenEvent.DismissBottomSheet)
            },

            onRegionSelected = {
                viewModel.onEvent(ClaimPrizeScreenEvent.SelectRegion(it))
                viewModel.onEvent(ClaimPrizeScreenEvent.DismissBottomSheet)
            },
            regions = viewModel.currentRegions.value,

            getRegions = {
                viewModel.onEvent(
                    ClaimPrizeScreenEvent.GetRegions(
                        context = context,
                        selectedCountry = viewModel.selectedCountry.value
                    )
                )
            },


            )
        {
            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CountryTextField(
                    label = "Select Country",
                    modifier = Modifier
                        .padding(top = 50.dp),
                    expanded = viewModel.isShowingBottomSheet.value,
                    selectedCountry = viewModel.selectedCountry.value
                ) {
                    viewModel.onEvent(ClaimPrizeScreenEvent.ShowBottomSheet(true))
                }

                RegionTextField(
                    label = "Select Region",
                    modifier = Modifier
                        .padding(top = 50.dp),
                    expanded = viewModel.isShowingBottomSheet.value,
                    selectedRegion = viewModel.selectedRegion.value
                ) {
                    viewModel.onEvent(ClaimPrizeScreenEvent.ShowBottomSheet(false))
                }
            }
        }

    }
}

