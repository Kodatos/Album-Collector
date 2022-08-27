package com.kodatos.albumcollector.core.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.widget.EditText
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.viewbinding.ViewBinding
import com.google.android.material.R
import com.google.android.material.button.MaterialButton

val ViewBinding.context: Context
    get() = root.context

val Context.dynamicColors: AppDynamicColors
    @SuppressLint("ResourceType")
    get() {
        val ta = obtainStyledAttributes(
            intArrayOf(
                R.attr.colorPrimary,
                R.attr.colorOnPrimary,
                R.attr.colorSecondary,
                R.attr.colorOnSecondary,
                R.attr.colorPrimaryContainer,
                R.attr.colorOnPrimaryContainer,
            )
        )
        val appDynamicColors = AppDynamicColors(
            ta.getColor(0, 0),
            ta.getColor(1, 0),
            ta.getColor(2, 0),
            ta.getColor(3, 0),
            ta.getColor(4, 0),
            ta.getColor(5, 0),
        )
        ta.recycle()
        return appDynamicColors
    }

fun MaterialButton.setTint(@ColorInt color: Int){
    setTextColor(color)
    iconTint = ColorStateList.valueOf(color)
}

fun ImageView.setTint(@ColorInt color: Int){
    imageTintList = ColorStateList.valueOf(color)
}

val Int.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()

val EditText.textString: String?
    get() = text?.toString()