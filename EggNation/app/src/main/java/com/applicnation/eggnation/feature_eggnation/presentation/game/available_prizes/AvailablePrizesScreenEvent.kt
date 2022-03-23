package com.applicnation.eggnation.feature_eggnation.presentation.game.available_prizes

import com.applicnation.eggnation.R
import com.applicnation.eggnation.feature_eggnation.domain.modal.AvailablePrize

sealed class AvailablePrizesScreenEvent {
    data class ShowPrizeInfo(
        val prizeImage: Int = R.drawable.egg,
        val prize: AvailablePrize = AvailablePrize(
            prizeTitle = "Failed to fetch prize title",
            prizeDesc = "Failed to fetch prize description"
        )
    ) : AvailablePrizesScreenEvent()

    object HidePrizeInfo : AvailablePrizesScreenEvent()

    object FetchAvailablePrizes : AvailablePrizesScreenEvent()
}