package com.applicnation.eggnation.feature_eggnation.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun StandardTextField(
    isPassword: Boolean = false,
    label: String = "",
    text: String = "",
    onValueChange: (String) -> Unit,
    maxLength: Int = 30,
    isError: Boolean = false,
    errorText: String = "",
    keyboardActions: KeyboardActions = KeyboardActions(),
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
    textFieldColors: TextFieldColors = TextFieldDefaults.outlinedTextFieldColors(),
    modifier: Modifier,
    isIconClickable: Boolean,
) {
    val isPasswordToggleDisplayed by remember { mutableStateOf(isPassword) }
    var isPasswordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
    ) {
        OutlinedTextField(
            label = { Text(text = label) },
            value = text,
            onValueChange = { if (it.length <= maxLength) onValueChange(it) },
            isError = isError && text.isNotEmpty(),
            singleLine = true,
            colors = textFieldColors,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            visualTransformation = if (isPasswordToggleDisplayed && !isPasswordVisible) PasswordVisualTransformation() else VisualTransformation.None,
            trailingIcon = {
                if (isPasswordToggleDisplayed) {
                    IconButton(enabled = isIconClickable, onClick = { isPasswordVisible = !isPasswordVisible }) {
                        Icon(
                            imageVector = if (isPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                            contentDescription = if (isPasswordVisible) "hide password" else "show password"
                        )
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        if (isError && text.isNotEmpty()) {
            ErrorField(
                error = errorText,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = 4.dp)
            )
        }
    }

}
