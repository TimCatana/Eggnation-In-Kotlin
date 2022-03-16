package com.applicnation.eggnation.feature_eggnation.presentation.game.claim_prize

import android.content.Context
import com.applicnation.eggnation.feature_eggnation.domain.modal.Country
import com.applicnation.eggnation.feature_eggnation.domain.modal.Region

sealed class ClaimPrizeScreenEvent {
    data class ShowBottomSheet(val isCountryList: Boolean): ClaimPrizeScreenEvent()
    object DismissBottomSheet: ClaimPrizeScreenEvent()
    data class SelectCountry(val country: Country): ClaimPrizeScreenEvent()
    data class SelectRegion(val region: Region): ClaimPrizeScreenEvent()
    data class GetRegions(val context: Context, val selectedCountry: Country): ClaimPrizeScreenEvent()
}