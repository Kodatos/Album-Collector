package com.kodatos.albumcollector.core.adapter

import com.kodatos.albumcollector.R
import com.kodatos.albumcollector.databinding.ListLoaderItemBinding

fun listLoaderItem() = RecyclerViewItem(
    layoutID = R.layout.list_loader_item,
    getBinding = ListLoaderItemBinding::bind
).fullSizeItem()