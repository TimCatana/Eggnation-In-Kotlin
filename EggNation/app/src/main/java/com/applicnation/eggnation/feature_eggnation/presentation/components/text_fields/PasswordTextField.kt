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
fun PasswordTextField(
    text: String,
    onValueChange: (String) -> Unit,
    isError: Boolean,
    imeAction: ImeAction,
    keyboardActions: KeyboardActions,
    textFieldColors: TextFieldColors = TextFieldDefaults.outlinedTextFieldColors(),
    modifier: Modifier,
    isIconClickable: Boolean,
) {
    StandardTextField(
        isPassword = true,
        label = stringResource(id = R.string.passwordTFLabel),
        text = text,
        onValueChange = onValueChange,
        isError = isError,
        errorText = stringResource(id = R.string.passwordTFError),
        textFieldColors = textFieldColors,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = imeAction
        ),
        keyboardActions = keyboardActions,
        isIconClickable = isIconClickable,
        modifier = modifier
    )
}
