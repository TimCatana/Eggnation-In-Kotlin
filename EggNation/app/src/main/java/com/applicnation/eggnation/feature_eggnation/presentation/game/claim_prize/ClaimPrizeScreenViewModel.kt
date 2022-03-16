package com.applicnation.eggnation.feature_eggnation.presentation.game.claim_prize

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.applicnation.eggnation.feature_eggnation.domain.modal.Countries
import com.applicnation.eggnation.feature_eggnation.domain.modal.Country
import com.applicnation.eggnation.feature_eggnation.domain.modal.Region
import com.applicnation.eggnation.feature_eggnation.domain.modal.WonPrize
import com.applicnation.eggnation.feature_eggnation.domain.repository.AuthenticationRepository
import com.applicnation.eggnation.feature_eggnation.domain.use_case.MainGameLogicUseCases
import com.applicnation.eggnation.feature_eggnation.domain.use_case.PrizeUseCases
import com.applicnation.eggnation.feature_eggnation.domain.use_case.UserUseCases
import com.applicnation.eggnation.feature_eggnation.presentation.game.won_prizes.WonPrizesScreenEvent
import com.applicnation.eggnation.util.Resource
import countryList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import regionList
import javax.inject.Inject

@HiltViewModel
class ClaimPrizeScreenViewModel @Inject constructor(
//    private val preferencesUseCases: PreferencesUseCases
) : ViewModel() {

    private val _isCountryList = mutableStateOf(true)
    val isCountryList: State<Boolean> = _isCountryList

    private val _isShowingBottomSheet = mutableStateOf(false)
    val isShowingBottomSheet: State<Boolean> = _isShowingBottomSheet

    private val _isShowCountries = mutableStateOf(true)
    val isShowCountries: State<Boolean> = _isShowCountries

    private val _selectedCountry = mutableStateOf(Country())
    val selectedCountry: State<Country> = _selectedCountry

    private val _isShowingRegionBottomSheet = mutableStateOf(false)
    val isShowingRegionBottomSheet: State<Boolean> = _isShowingRegionBottomSheet

    private val _selectedRegion = mutableStateOf(Region())
    val selectedRegion: State<Region> = _selectedRegion

    private val _currentRegions = mutableStateOf(arrayListOf(
        Region("Badakhshan", "BDS"),
        Region("Badghis", "BDG"),
        Region("Baghlan", "BGL"),
        Region("Balkh", "BAL"),
        Region("Bamyan", "BAM"),
        Region("Daykundi", "DAY"),
        Region("Farah", "FRA"),
        Region("Faryab", "FYB"),
        Region("Ghazni", "GHA"),
        Region("Ghor", "GHO"),
        Region("Helmand", "HEL"),
        Region("Herat", "HER"),
        Region("Jowzjan", "JOW"),
        Region("Kabul", "KAB"),
        Region("Kandahar", "KAN"),
        Region("Kapisa", "KAP"),
        Region("Khost", "KHO"),
        Region("Kunar", "KNR"),
        Region("Kunduz", "KDZ"),
        Region("Laghman", "LAG"),
        Region("Logar", "LOW"),
        Region("Maidan Wardak", "WAR"),
        Region("Nangarhar", "NAN"),
        Region("Nimruz", "NIM"),
        Region("Nuristan", "NUR"),
        Region("Paktia", "PIA"),
        Region("Paktika", "PKA"),
        Region("Panjshir", "PAN"),
        Region("Parwan", "PAR"),
        Region("Samangan", "SAM"),
        Region("Sar-e Pol", "SAR"),
        Region("Takhar", "TAK"),
        Region("Urozgan", "ORU"),
        Region("Zabul", "ZAB")
    ))
    val currentRegions: State<ArrayList<Region>> = _currentRegions

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
    }

    fun onEvent(event: ClaimPrizeScreenEvent) {
        when (event) {
            is ClaimPrizeScreenEvent.ShowBottomSheet -> {
                _isShowingBottomSheet.value = true
                _isCountryList.value = event.isCountryList
            }
            is ClaimPrizeScreenEvent.SelectCountry -> {
                _selectedCountry.value = event.country
            }
            is ClaimPrizeScreenEvent.SelectRegion -> {
                _selectedRegion.value = event.region
            }
            is ClaimPrizeScreenEvent.GetRegions -> {
                _currentRegions.value = regionList(event.context, event.selectedCountry)
                _selectedRegion.value = _currentRegions.value[0]
            }
            is ClaimPrizeScreenEvent.DismissBottomSheet -> {
                _isShowingBottomSheet.value = false
            }
        }
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
    }

}