package com.applicnation.eggnation.feature_eggnation.presentation.components

import com.applicnation.eggnation.R

fun getPrizeImage(prizeType: String): Int  {
    when (prizeType) {
        "earbuds" -> {
            return R.drawable.egg
        }
        "phone" -> {
            return R.drawable.egg_four
        }
        "tablet" -> {
            return  R.drawable.egg_three
        }
        "laptop" -> {
            return  R.drawable.egg
        }
        "tee" -> {
            return  R.drawable.egg_two
        }
        "hoodie" -> {
            return  R.drawable.egg
        }
        "cash" -> {
            return  R.drawable.egg_four
        }
        else -> {
            return  R.drawable.egg_three
        }
    }
}