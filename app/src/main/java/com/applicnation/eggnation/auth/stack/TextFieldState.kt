package com.applicnation.eggnation.auth.stack

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue


open class TextFieldState(
    private val validator: (String) -> Boolean = { true },
    private val errorMessage: (String) -> String
) {

    var text by mutableStateOf("")

    var error by mutableStateOf<String?>(null)

    fun validate() {
        error = if (validator(text)) {
            null
        } else {
            errorMessage(text)
        }

    }
}