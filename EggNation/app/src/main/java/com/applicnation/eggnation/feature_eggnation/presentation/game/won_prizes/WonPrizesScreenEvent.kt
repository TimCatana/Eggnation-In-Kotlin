package com.applicnation.eggnation.feature_eggnation.presentation.game.won_prizes

import com.applicnation.eggnation.R
import com.applicnation.eggnation.feature_eggnation.domain.modal.AvailablePrize
import com.applicnation.eggnation.feature_eggnation.domain.modal.WonPrize
import com.applicnation.eggnation.feature_eggnation.presentation.game.available_prizes.AvailablePrizesScreenEvent

sealed class WonPrizesScreenEvent {
    data class ShowPrizeInfo(
        val prizeImage: Int = R.drawable.egg,
        val prize: WonPrize = WonPrize(
            prizeTitle = "Failed to fetch prize title",
            prizeDesc = "Failed to fetch prize description",
            prizeId = "",
            prizeClaimed = true,
        )
    ) : WonPrizesScreenEvent()
    object HidePrizeInfo : WonPrizesScreenEvent()
    object FetchAvailablePrizes : WonPrizesScreenEvent()
    object GoToClaimScreenIfValid : WonPrizesScreenEvent()
}