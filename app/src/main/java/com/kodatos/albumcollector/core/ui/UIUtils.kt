package com.kodatos.albumcollector.core.ui

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.widget.EditText
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.viewbinding.ViewBinding
import coil.load
import com.google.android.material.button.MaterialButton
import com.google.android.material.imageview.ShapeableImageView

val ViewBinding.context: Context
    get() = root.context

val Context.dynamicColors: AppDynamicColors
    get() = AppDynamicColors.getInstance(this)

fun MaterialButton.setTint(@ColorInt color: Int) {
    setTextColor(color)
    iconTint = ColorStateList.valueOf(color)
}

fun ImageView.setTint(@ColorInt color: Int) {
    imageTintList = ColorStateList.valueOf(color)
}

val EditText.textString: String
    get() = text?.toString().orEmpty()


val EditText.textOrNull: String?
    get() = text?.toString()?.ifBlank { null }

fun ShapeableImageView.loadUrlOrTintPlaceholder(
    url: String?,
    @DrawableRes placeHolder: Int,
    @ColorInt tint: Int = Color.TRANSPARENT,
    placeholderPadding: Int = 0,
    loadListener: () -> Unit = {}
) {
    load(url) {
        error(placeHolder)
        placeholder(placeHolder)
        listener(
            onError = { req, res ->
                loadListener()
                setTint(tint)
                setContentPadding(
                    placeholderPadding,
                    placeholderPadding,
                    placeholderPadding,
                    placeholderPadding
                )
            },
            onSuccess = { req, res ->
                loadListener()
                setContentPadding(0, 0, 0, 0)
                imageTintList = null
            }
        )
    }
}