package com.kodatos.albumcollector.core.ui

import com.kodatos.albumcollector.R
import com.kodatos.albumcollector.databinding.ListLoaderItemBinding

fun listLoaderItem() = RecyclerViewItem(
    R.layout.list_loader_item,
    getBinding = ListLoaderItemBinding::bind
) {}