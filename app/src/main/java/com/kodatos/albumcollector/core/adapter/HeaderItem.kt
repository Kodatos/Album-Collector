package com.kodatos.albumcollector.core.adapter

import android.view.ViewGroup
import androidx.core.view.updateLayoutParams
import com.kodatos.albumcollector.R
import com.kodatos.albumcollector.databinding.HeaderItemBinding

fun headerItem(text: String, marginBottom: Int = 0) = RecyclerViewItem(
    layoutID = R.layout.header_item,
    getBinding = HeaderItemBinding::bind
) {
    header.text = text
    header.updateLayoutParams<ViewGroup.MarginLayoutParams> {
        bottomMargin = marginBottom
    }
}