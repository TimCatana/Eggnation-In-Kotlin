package com.applicnation.eggnation.feature_eggnation.presentation.game.store

import com.applicnation.eggnation.feature_eggnation.domain.modal.Skin

sealed class StoreScreenEvent {
    data class SelectSkin(val skin: Int): StoreScreenEvent()
}
