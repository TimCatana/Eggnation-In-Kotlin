package com.applicnation.eggnation.feature_eggnation.presentation.game.claim_prize

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.applicnation.eggnation.feature_eggnation.domain.modal.Country
import com.applicnation.eggnation.feature_eggnation.domain.modal.Region
import com.applicnation.eggnation.feature_eggnation.presentation.auth.login.LoginScreenEvent
import com.applicnation.eggnation.feature_eggnation.presentation.auth.login.LoginScreenViewModel
import com.applicnation.eggnation.feature_eggnation.presentation.components.StandardTextField
import com.applicnation.eggnation.feature_eggnation.presentation.game.claim_prize.components.CountryPickerBottomSheet
import com.applicnation.eggnation.feature_eggnation.presentation.game.claim_prize.components.CountryTextField
import com.applicnation.eggnation.feature_eggnation.presentation.game.claim_prize.components.RegionPickerBottomSheet
import com.applicnation.eggnation.feature_eggnation.presentation.game.claim_prize.components.RegionTextField
import com.applicnation.eggnation.feature_eggnation.presentation.game.won_prizes.WonPrizesScreenViewModel
import com.applicnation.eggnation.feature_eggnation.presentation.navigation.GameNavGraph
import com.applicnation.eggnation.feature_eggnation.presentation.navigation.GameScreen
import countryList
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

@ExperimentalMaterialApi
@Composable
fun ClaimPrizeScreen(
    prizeId: String,
    navController: NavController,
    viewModel: ClaimPrizeScreenViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val countries = remember { countryList(context) }

    val scaffoldState = rememberScaffoldState()
    val localFocusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is ClaimPrizeScreenViewModel.UiEvent.ShowErrorSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                    )
                }
                is ClaimPrizeScreenViewModel.UiEvent.ShowSuccessSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                    )
                    navController.navigate(GameScreen.WonPrizes.route) {
                        popUpTo(GameScreen.Home.route)
                    }
                }
            }
        }
    }

    LaunchedEffect(key1 = viewModel.selectedCountry.value) {
        viewModel.onEvent(
            ClaimPrizeScreenEvent.GetRegions(
                context = context,
                selectedCountry = viewModel.selectedCountry.value!!
            )
        )
    }

    Scaffold(
        scaffoldState = scaffoldState,
    ) {
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

                    StandardTextField(
                        text = viewModel.addressText.value,
                        maxLength = 300,
                        onValueChange = {
                            viewModel.onEvent(ClaimPrizeScreenEvent.EnteredAddress(it))
                        },
                        isError = false,
                        errorText = "invalid address",
                        label = "address", // TODO - use string resources
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = { localFocusManager.clearFocus() }
                        )
                    )

                    Button(onClick = {
                        viewModel.onEvent(
                            ClaimPrizeScreenEvent.ClaimPrize(
                                viewModel.selectedCountry.value.toString(),
                                viewModel.selectedRegion.value.toString(),
                                viewModel.addressText.value,
                                prizeId,
                            )
                        )
                    }) {
                        Text(text = "Claim")
                    }
                }
            }
        }
    }
}

