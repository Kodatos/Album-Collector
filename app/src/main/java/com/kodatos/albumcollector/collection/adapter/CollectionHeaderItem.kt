package com.kodatos.albumcollector.collection.adapter

import android.widget.TextView
import com.kodatos.albumcollector.R
import com.kodatos.albumcollector.collection.models.CollectionModel
import com.kodatos.albumcollector.core.adapter.RecyclerViewItem
import com.kodatos.albumcollector.core.ui.context
import com.kodatos.albumcollector.core.ui.dynamicColors
import com.kodatos.albumcollector.core.ui.loadUrlOrTintPlaceholder
import com.kodatos.albumcollector.databinding.CollectionHeaderItemBinding

fun collectionHeaderItem(collectionModel: CollectionModel) =
    RecyclerViewItem<CollectionHeaderItemBinding>(
        R.layout.collection_header_item,
        getBinding = CollectionHeaderItemBinding::bind,
    ) {
        collectionTitle.text = collectionModel.name
        collectionImg.loadUrlOrTintPlaceholder(
            collectionModel.imageURL,
            R.drawable.ic_album,
            context.dynamicColors.secondary
        )
    }
