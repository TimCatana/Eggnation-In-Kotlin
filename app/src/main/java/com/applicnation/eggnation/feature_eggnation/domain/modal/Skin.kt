package com.applicnation.eggnation.feature_eggnation.domain.modal

import com.applicnation.eggnation.R

/**
 * @param skinName (String) The name of the skin
 * @param skinImage (Int) A drawable resource file
 */
data class Skin(
    var skinName: String,
    var skinImage: Int
)


object SkinsList {
    val skins = arrayListOf(
        Skin("name1", R.drawable.egg),
        Skin("name2", R.drawable.egg_one ),
        Skin("name3", R.drawable.egg_two ),
        Skin("name4", R.drawable.egg_three ),
        Skin("name5", R.drawable.egg_four),
        Skin("name1", R.drawable.egg),
        Skin("name2", R.drawable.egg_one ),
        Skin("name3", R.drawable.egg_two ),
        Skin("name4", R.drawable.egg_three ),
        Skin("name5", R.drawable.egg_four),
        Skin("name1", R.drawable.egg),
        Skin("name2", R.drawable.egg_one ),
        Skin("name3", R.drawable.egg_two ),
        Skin("name4", R.drawable.egg_three ),
        Skin("name5", R.drawable.egg_four),
        Skin("name1", R.drawable.egg),
        Skin("name2", R.drawable.egg_one ),
        Skin("name3", R.drawable.egg_two ),
        Skin("name4", R.drawable.egg_three ),
        Skin("name5", R.drawable.egg_four),
        Skin("name1", R.drawable.egg),
        Skin("name2", R.drawable.egg_one ),
        Skin("name3", R.drawable.egg_two ),
        Skin("name4", R.drawable.egg_three ),
        Skin("name5", R.drawable.egg_four),
    )
}