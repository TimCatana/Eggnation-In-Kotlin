package com.applicnation.eggnation.feature_eggnation.presentation.auth.login

import androidx.compose.ui.focus.FocusState

sealed class LoginScreenEvent{
    data class EnteredEmail(val value: String): LoginScreenEvent()
    data class EnteredPassword(val value: String): LoginScreenEvent()
    data class SignIn(val email: String, val password: String): LoginScreenEvent()
    // TODO - maybe do some focus changing stuff for the text fields

//    data class EnteredTitle(val value: String): LoginScreenEvent()
//    data class ChangeTitleFocus(val focusState: FocusState): LoginScreenEvent()
//    data class EnteredContent(val value: String): LoginScreenEvent()
//    data class ChangeContentFocus(val focusState: FocusState): LoginScreenEvent()
//    data class ChangeColor(val color: Int): LoginScreenEvent()
//    object SaveNote: LoginScreenEvent()
}