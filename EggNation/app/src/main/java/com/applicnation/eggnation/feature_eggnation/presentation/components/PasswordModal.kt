package com.applicnation.eggnation.feature_eggnation.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.applicnation.eggnation.feature_eggnation.presentation.game.settings.SettingsScreenEvent
import com.applicnation.eggnation.ui.theme.PasswordModalBG

@Composable
fun PasswordModal(
    modifier: Modifier,
    text: String,
    onTextChange: (String) -> Unit,
    cancelModal: () -> Unit,
    onDone: () -> Unit
) {
    val localFocusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(25.dp),
        elevation = 4.dp,
        backgroundColor = PasswordModalBG
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                StandardTextField(
                    text = text,
                    onValueChange = { onTextChange(it) },
                    isError = false,
                    errorText = "Please enter a password",
                    keyboardType = KeyboardType.Password,
                    label = "password", // TODO - use string resources
                    isPassword = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp, 0.dp)
                        .focusRequester(focusRequester),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            localFocusManager.clearFocus()
                            onDone()
                        }
                    ),
                    textFieldColors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.White,
                        focusedLabelColor = Color.White,
                        unfocusedBorderColor = Color.White,
                        unfocusedLabelColor = Color.White,
                        trailingIconColor = Color.White,
                        cursorColor = Color.White,
                        textColor = Color.White
                    ),
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = cancelModal) {
                    Text(text = "Cancel")
                }
            }
        }
    }
}