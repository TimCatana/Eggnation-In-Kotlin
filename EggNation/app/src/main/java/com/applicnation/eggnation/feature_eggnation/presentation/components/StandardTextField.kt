package com.applicnation.eggnation.feature_eggnation.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.applicnation.eggnation.ui.theme.Purple200

// TODO - get rid of hint and add label paramater

@Composable
fun StandardTextField(
    text: String = "",
    label: String = "",
    maxLength: Int = 30,
    isError: Boolean = false,
    errorText: String = "",
    keyboardActions: KeyboardActions = KeyboardActions(),
    keyboardOptions: KeyboardOptions =   KeyboardOptions(keyboardType = KeyboardType.Text,),
    keyboardType: KeyboardType = KeyboardType.Text,
    onValueChange: (String) -> Unit,
    modifier: Modifier,
    isPassword: Boolean = false,
    imeAction: ImeAction = ImeAction.Default,
    textFieldColors: TextFieldColors = TextFieldDefaults.outlinedTextFieldColors()
) {
    val isPasswordToggleDisplayed by remember {
        mutableStateOf(isPassword)
    }
    var isPasswordVisible by remember {
        mutableStateOf(false)
    }

    OutlinedTextField(
        value = text,
        onValueChange = {
            if (it.length <= maxLength) {
                onValueChange(it)
            }
        },
        label = { Text(text = label ) },
        isError = isError && text.isNotEmpty(),
        singleLine = true,
        colors = textFieldColors,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        visualTransformation = if (isPasswordToggleDisplayed && !isPasswordVisible) {
            PasswordVisualTransformation()
        } else {
            VisualTransformation.None
        },
        trailingIcon = {
            if (isPasswordToggleDisplayed) {
                IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                    Icon(
                        imageVector = if (isPasswordVisible) {
                            Icons.Filled.Visibility
                        } else {
                            Icons.Filled.VisibilityOff
                        },
                        contentDescription = "toggle password" // you can also add an if-else for descriptions for different icons - probably do that
                    )
                }
            }
        },
        modifier = modifier
    )

    if (isError && text.isNotEmpty()) {
        ErrorField(error = errorText)
    }
}


@Preview(showBackground = true)
@Composable
fun StandardTextFieldPreview(
) {
    MaterialTheme() {
        OutlinedTextField(
            value = "test",
            onValueChange = {
                //  TODO
            },
            label = { Text(text = "Label") },
            isError = false,
            keyboardOptions = KeyboardOptions(
                // TODO - edit IME action
            ),
            // TODO visual Transformation
            singleLine = true,
            trailingIcon = {
                IconButton(onClick = {}) {
                    Icon(
                        imageVector =
                        Icons.Filled.Visibility,
                        contentDescription = "toggle password" // you can also add an if-else for descriptions for different icons - probably do that
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .semantics {
                    testTag = "standardTextField"
                }
        )
    }
}