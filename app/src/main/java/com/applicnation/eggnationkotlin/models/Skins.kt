package com.applicnation.eggnationkotlin.models

import com.applicnation.eggnationkotlin.R
import com.applicnation.eggnationkotlin.utils.SkinAdapter


data class Skin(var skinName: String, var skinImage: Int, var viewType: Int)

object SkinsSupplier {
    val skins = arrayListOf<Skin>(
        Skin("name1", R.drawable.egg, SkinAdapter.VIEW_TYPE_ONE),
        Skin("name2", R.drawable.egg_one, SkinAdapter.VIEW_TYPE_ONE),
        Skin("name3", R.drawable.egg_two, SkinAdapter.VIEW_TYPE_ONE),
        Skin("name4", R.drawable.egg_three, SkinAdapter.VIEW_TYPE_TWO),
        Skin("name5", R.drawable.egg_four, SkinAdapter.VIEW_TYPE_TWO),
    )
}