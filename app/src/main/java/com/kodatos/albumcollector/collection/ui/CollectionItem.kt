package com.kodatos.albumcollector.collection.ui

import com.kodatos.albumcollector.R
import com.kodatos.albumcollector.collection.models.CollectionModel
import com.kodatos.albumcollector.core.ui.RecyclerViewItem
import com.kodatos.albumcollector.databinding.CollectionItemBinding

fun collectionItem(collectionModel: CollectionModel) = RecyclerViewItem(
    R.layout.collection_item,
    getBinding = CollectionItemBinding::bind
) {

}