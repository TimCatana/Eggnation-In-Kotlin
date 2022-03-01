package com.applicnation.eggnation.feature_eggnation.presentation.game.claim_prize

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.applicnation.eggnation.feature_eggnation.domain.modal.Country
import com.applicnation.eggnation.feature_eggnation.domain.modal.Region
import com.applicnation.eggnation.feature_eggnation.presentation.game.claim_prize.components.CountryPickerBottomSheet
import com.applicnation.eggnation.feature_eggnation.presentation.game.claim_prize.components.CountryTextField

@ExperimentalMaterialApi
@Composable
fun ClaimPrizeScreen(
    navController: NavController
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedCountry by remember { mutableStateOf<Country?>(null) }

    Box {
        CountryPickerBottomSheet(
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
                selectedCountry = it
                expanded = false
            },
            show = expanded,
            onDismissRequest = {
                expanded = false
            }
        ) {
            CountryTextField(
                label = "Select Country",
                modifier = Modifier
                    .padding(top = 50.dp)
                    .align(Alignment.TopCenter),
                expanded = expanded,
                selectedCountry = selectedCountry
            ) {
                expanded = !expanded
            }
        }

    }
}

