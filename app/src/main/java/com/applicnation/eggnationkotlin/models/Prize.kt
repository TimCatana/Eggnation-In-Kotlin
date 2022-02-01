package com.applicnation.eggnationkotlin.models

import com.applicnation.eggnationkotlin.R

// TODO - might add the images I want to show in cloud storage
data class Prize (
    var prizeId: String = "",
    var prizeName: String = "",
    var prizeType: String = "",
)

object WonPrizeSupplier {
    val wonPrizes = arrayListOf(
        Prize("c83mar983rredhfm8", "macbook", "laptop"),
        Prize("c83masfsr3r98hfm8", "iPhone", "phone"),
        Prize("c83marfdse398hfm8", "iPhone", "phone"),
        Prize("3rfsefgh45thgefsf", "macbook", "laptop"),
        Prize("c83mar93r3ee8hfm8", "macbook", "laptop"),
        Prize("c83marfdse398hfm8", "iPhone", "phone"),
        Prize("3rfsefgh45thgefsf", "macbook", "laptop"),
    )
}