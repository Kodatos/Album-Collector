package com.kodatos.albumcollector.core.adapter

import com.kodatos.albumcollector.R
import com.kodatos.albumcollector.databinding.EmptyCollectionItemBinding

fun emptyCollectionItem() = RecyclerViewItem(
    layoutID = R.layout.empty_collection_item,
    getBinding = EmptyCollectionItemBinding::bind
).fullSizeItem()