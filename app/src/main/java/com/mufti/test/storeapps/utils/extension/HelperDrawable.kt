package com.mufti.test.storeapps.utils.extension

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat

object HelperDrawable {
    fun getDrawableCompat(context: Context, drawableResId: Int): Drawable? {
        return ContextCompat.getDrawable(context, drawableResId)
    }
}