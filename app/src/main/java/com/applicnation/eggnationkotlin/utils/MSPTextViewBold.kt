package com.applicnation.eggnationkotlin.utils

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class MSPTextViewBold(context: Context, attrs: AttributeSet) : AppCompatTextView(context, attrs) {

    init {
        applyFont()
    }

    private fun applyFont() {
        val typeface: Typeface = Typeface.createFromAsset(context.assets, "path-to-font.ext")
        setTypeface(typeface)
    }

    /**
     *
     * now i can set custom views in desifn xml files using:
     *
     * <package.MSPTextViewBold>
     *
     * </package.MSPTextViewBold>
     */


}