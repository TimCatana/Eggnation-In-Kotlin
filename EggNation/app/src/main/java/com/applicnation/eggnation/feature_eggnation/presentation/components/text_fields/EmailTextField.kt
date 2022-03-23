package com.applicnation.eggnation.feature_eggnation.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.applicnation.eggnation.R

@Composable
fun EmailTextField(
    text: String,
    onValueChange: (String) -> Unit,
    isError: Boolean,
    imeAction: ImeAction,
    keyboardActions: KeyboardActions,
    textFieldColors: TextFieldColors = TextFieldDefaults.outlinedTextFieldColors(),
    modifier: Modifier,
) {
    StandardTextField(
        label = stringResource(id = R.string.emailTFLabel),
        text = text,
        onValueChange = onValueChange,
        maxLength = 75,
        isError = isError,
        errorText = stringResource(id = R.string.emailTFError),
        textFieldColors = textFieldColors,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = imeAction
        ),
        keyboardActions = keyboardActions,
        isIconClickable = false, // There is no icon to click
        modifier = modifier,
    )
}