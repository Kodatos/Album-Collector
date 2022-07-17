package com.kodatos.albumcollector.core.ui

import com.kodatos.albumcollector.R
import com.kodatos.albumcollector.databinding.HeaderItemBinding

fun headerItem(text: String) = RecyclerViewItem(
    R.layout.header_item,
    getBinding = HeaderItemBinding::bind
) {
    header.text = text
}