package com.applicnation.eggnation.feature_eggnation.presentation.game.claim_prize.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.unit.dp
import com.applicnation.eggnation.feature_eggnation.domain.modal.Country
import countryList
import kotlinx.coroutines.coroutineScope
import localeToEmoji

@ExperimentalMaterialApi
@Composable
fun CountryPickerBottomSheet(
    title: @Composable () -> Unit,
    show: Boolean,
    onItemSelected: (country: Country) -> Unit,
    onDismissRequest: () -> Unit,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val countries = remember { countryList(context) }
    var selectedCountry by remember { mutableStateOf(countries.countries[0]) }
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
            title()
            LazyColumn(
                contentPadding = PaddingValues(16.dp)
            ) {
                items(countries.countries.size) { index ->
                    Row(
                        modifier = Modifier
                            .clickable {
                                selectedCountry = countries.countries[index]
                                onItemSelected(selectedCountry)
                            }
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
        },
        sheetState = modalBottomSheetState,
        sheetShape = RoundedCornerShape(
            topStart = 20.dp, topEnd = 20.dp
        )
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
    selectedCountry: Country? = null,
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

@Composable
fun Modifier.expandable(
    onExpandedChange: () -> Unit,
    menuLabel: String
) = pointerInput(Unit) {
    forEachGesture {
        coroutineScope {
            awaitPointerEventScope {
                var event: PointerEvent
                do {
                    event = awaitPointerEvent(PointerEventPass.Initial)
                } while (!event.changes.all {
                        it.changedToUp()
                    })
                onExpandedChange.invoke()
            }
        }
    }
}.semantics {
    contentDescription = menuLabel
    onClick {
        onExpandedChange()
        true
    }
}