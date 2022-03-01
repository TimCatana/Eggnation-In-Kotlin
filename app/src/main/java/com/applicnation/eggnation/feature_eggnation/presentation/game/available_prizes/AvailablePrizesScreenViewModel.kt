package com.applicnation.eggnation.feature_eggnation.presentation.game.available_prizes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.applicnation.eggnation.feature_eggnation.domain.modal.AvailablePrize
import com.applicnation.eggnation.feature_eggnation.domain.use_case.PrizeUseCases
import com.applicnation.eggnation.feature_eggnation.presentation.auth.login.LoginScreenViewModel
import com.applicnation.eggnation.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AvailablePrizesScreenViewModel @Inject constructor(
//    private val preferencesUseCases: PreferencesUseCases
    private val prizeUseCases: PrizeUseCases
) : ViewModel() {

    private val _showPrizeInfo = mutableStateOf(false)
    val showPrizeInfo: State<Boolean> = _showPrizeInfo

    private val _prizes = mutableStateOf(ArrayList<AvailablePrize>())
    val prizes: State<ArrayList<AvailablePrize>> = _prizes

    // TODO - probably make the below three it's own component with viewModel in the future
    private val _prizeTitleInfo = mutableStateOf("")
    val prizeTitleInfo: State<String> = _prizeTitleInfo

    private val _prizeDescInfo = mutableStateOf("")
    val prizeDescInfo: State<String> = _prizeDescInfo

    private val _prizeImageInfo = mutableStateOf(0)
    val prizeImageInfo: State<Int> = _prizeImageInfo

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        prizeUseCases.availablePrizeGetAllUC()
            .onEach { result ->
                when (result) {
                    is Resource.Loading -> {
                        _isLoading.value = true
                    }
                    is Resource.Success -> {
                        _isLoading.value = false
                        _prizes.value = result.data!!
                    }
                    is Resource.Error -> {
                        _isLoading.value = false
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                message = result.message!!
                            )
                        )
                    }
                }
            }.launchIn(viewModelScope)
    }


    fun onEvent(event: AvailablePrizesScreenEvent) {
        when (event) {
            is AvailablePrizesScreenEvent.ShowPrizeInfo -> {
                _prizeTitleInfo.value = event.prizeTitle
                _prizeDescInfo.value = event.prizeDesc
                _prizeImageInfo.value = event.prizeImage

                _showPrizeInfo.value = event.showInfo
            }
            // TODO - add prize refresh event
            AvailablePrizesScreenEvent.FetchAvailablePrizes -> {
                TODO()
            }
        }
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
    }
}