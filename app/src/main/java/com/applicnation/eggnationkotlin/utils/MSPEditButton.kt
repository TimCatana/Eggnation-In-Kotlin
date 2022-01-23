package com.applicnation.eggnationkotlin.utils

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton

class MSPEditButton(context: Context, attrs: AttributeSet ): AppCompatButton(context, attrs) {

    init {
        applyFont()
    }

    private fun applyFont() {
        val typeface: Typeface = Typeface.createFromAsset(context.assets, "path-to-font.ext")
        setTypeface(typeface)
    }


}