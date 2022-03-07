package com.applicnation.eggnation.feature_eggnation.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.applicnation.eggnation.feature_eggnation.domain.modal.Country
import com.applicnation.eggnation.feature_eggnation.domain.modal.Region
import com.applicnation.eggnation.feature_eggnation.presentation.game.claim_prize.ClaimPrizeScreenEvent
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
) : ViewModel() {

    private val _isLoggedIn = mutableStateOf(false)
    val isLoggedIn: State<Boolean> = _isLoggedIn

    val mAuth = FirebaseAuth.getInstance()

    val authStateListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null) {
            _isLoggedIn.value = true
            Timber.i("game nav graphs should show")
        } else {
            _isLoggedIn.value = false
            Timber.i("auth nav graph should show")
        }
    }
}