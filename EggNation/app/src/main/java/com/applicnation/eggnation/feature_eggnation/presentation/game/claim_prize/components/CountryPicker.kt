package com.applicnation.eggnation.feature_eggnation.presentation.game.claim_prize.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.pointer.PointerEvent
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.changedToUp
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.applicnation.eggnation.feature_eggnation.domain.modal.Countries
import com.applicnation.eggnation.feature_eggnation.domain.modal.Country
import com.applicnation.eggnation.feature_eggnation.domain.modal.Region
import countryList
import kotlinx.coroutines.coroutineScope
import localeToEmoji

@ExperimentalMaterialApi
@Composable
fun CountryPickerBottomSheet(
    titleText: String,
    show: Boolean,
    isCountryList: Boolean,
    onDismissRequest: () -> Unit,
    modifier: Modifier,
    /** country stuff */
    countries: Countries,
    onCountrySelected: (country: Country) -> Unit,
    /** region stuff */
    regions: ArrayList<Region>,
    onRegionSelected: (region: Region) -> Unit,
    content: @Composable () -> Unit,
) {
    val modalBottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

    LaunchedEffect(key1 = show) {
        if (show) modalBottomSheetState.show() else modalBottomSheetState.hide()
    }

    LaunchedEffect(key1 = modalBottomSheetState.currentValue) {
        if (modalBottomSheetState.currentValue == ModalBottomSheetValue.Hidden) {
            onDismissRequest()
        }
    }

    ModalBottomSheetLayout(
        sheetContent = {
            Text(
                text = titleText,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )

            LazyColumn(
                contentPadding = PaddingValues(16.dp)
            ) {

                if (isCountryList) {
                    items(countries.countries.size) { index ->
                        Row(
                            modifier = Modifier
                                .clickable { onCountrySelected(countries.countries[index]) }
                                .padding(10.dp)
                        ) {
                            Text(
                                text = localeToEmoji(countryCode = countries.countries[index].countryShortCode)
                            )
                            Text(
                                text = countries.countries[index].countryName,
                                modifier = Modifier
                                    .padding(start = 6.dp)
                                    .weight(2f)
                            )
                        }

                        Divider(color = Color.LightGray, thickness = 0.5.dp)
                    }
                }

                if (!isCountryList) {
                    // TODO - show regions
                    items(regions.size) { index ->
                        Row(
                            modifier = Modifier
                                .clickable {
                                    onRegionSelected(regions[index])
                                }
                                .padding(10.dp)
                        ) {
                            Text(
                                text = regions[index].name,
                                modifier = Modifier
                                    .padding(start = 6.dp)
                                    .weight(2f)
                            )
                        }

                        Divider(color = Color.LightGray, thickness = 0.5.dp)
                    }
                }
            }
        },
        sheetState = modalBottomSheetState,
        sheetShape = RoundedCornerShape(
            topStart = 20.dp, topEnd = 20.dp
        ),
        modifier = modifier
    ) {
        content()
    }
}

@Composable
fun CountryTextField(
    label: String = "",
    isError: Boolean = false,
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.small,
    expanded: Boolean = false,
    selectedCountry: Country = Country(),
    colors: TextFieldColors = TextFieldDefaults.outlinedTextFieldColors(),
    onExpandedChange: () -> Unit
) {
    OutlinedTextField(
        value = selectedCountry?.countryName ?: "",
        onValueChange = {},
        modifier = modifier
            .expandable(menuLabel = label, onExpandedChange = onExpandedChange),
        readOnly = true,
        isError = isError,
        label = {
            Text(text = label)
        },
        colors = colors,
        shape = shape,
        trailingIcon = {
            Icon(
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = "Spinner arrow",
                modifier = Modifier
                    .rotate(
                        if (expanded) 180f else 0f
                    )
            )
        }
    )
}