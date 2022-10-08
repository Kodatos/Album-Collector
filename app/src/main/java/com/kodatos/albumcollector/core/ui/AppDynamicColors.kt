package com.kodatos.albumcollector.core.ui

import android.annotation.SuppressLint
import android.content.Context
import androidx.annotation.ColorInt
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import com.google.android.material.R
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped


@SuppressWarnings("ResourceType", "MagicNumber")
class AppDynamicColors private constructor(
     context: Context,
) {
    @ColorInt val primary: Int
    @ColorInt val onPrimary: Int
    @ColorInt val secondary: Int
    @ColorInt val onSecondary: Int
    @ColorInt val primaryContainer: Int
    @ColorInt val onPrimaryContainer: Int

    init {
        val ta = context.obtainStyledAttributes(
            intArrayOf(
                R.attr.colorPrimary,
                R.attr.colorOnPrimary,
                R.attr.colorSecondary,
                R.attr.colorOnSecondary,
                R.attr.colorPrimaryContainer,
                R.attr.colorOnPrimaryContainer,
            )
        )
        primary = ta.getColor(0, 0)
        onPrimary = ta.getColor(1, 0)
        secondary = ta.getColor(2, 0)
        onSecondary = ta.getColor(3, 0)
        primaryContainer = ta.getColor(4, 0)
        onPrimaryContainer = ta.getColor(5, 0)
        ta.recycle()

    }

    companion object {

        @Volatile
        private var INSTANCE: AppDynamicColors? = null

        fun getInstance(context: Context): AppDynamicColors = INSTANCE ?: synchronized(this) {
            INSTANCE ?: AppDynamicColors(context)
        }
    }
}
