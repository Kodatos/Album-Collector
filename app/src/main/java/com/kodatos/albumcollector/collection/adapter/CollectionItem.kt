package com.kodatos.albumcollector.collection.adapter

import androidx.core.graphics.ColorUtils
import androidx.core.view.isVisible
import coil.load
import com.kodatos.albumcollector.R
import com.kodatos.albumcollector.collection.models.CollectionModel
import com.kodatos.albumcollector.core.adapter.DiffRecyclerViewItem
import com.kodatos.albumcollector.core.ui.context
import com.kodatos.albumcollector.core.ui.dynamicColors
import com.kodatos.albumcollector.core.ui.setTint
import com.kodatos.albumcollector.databinding.CollectionItemBinding

fun collectionItem(collectionModel: CollectionModel, listener: CollectionItemListener) =
    DiffRecyclerViewItem(
        R.layout.collection_item,
        spanCount = 2,
        itemID = collectionModel.id,
        getBinding = CollectionItemBinding::bind,
    ) {
        editLayout.isVisible = false
        val dynamicColors = context.dynamicColors
        editLayout.setBackgroundColor(
            ColorUtils.setAlphaComponent(
                dynamicColors.primaryContainer,
                BG_ALPHA
            )
        )
        name.text = collectionModel.name
        img.load(collectionModel.imageURL)
        root.setOnClickListener { listener.onAction(CollectionItemListener.View) }
        root.setOnLongClickListener {
            editLayout.isVisible = !editLayout.isVisible
            true
        }
        editLayout.setOnClickListener { editLayout.isVisible = false }
        edit.setOnClickListener {
            listener.onAction(CollectionItemListener.Edit)
            editLayout.isVisible = false
        }
        edit.setTint(dynamicColors.onPrimaryContainer)
        close.setTint(dynamicColors.onPrimaryContainer)
        delete.setOnClickListener {
            listener.onAction(CollectionItemListener.Delete)
            editLayout.isVisible = false
        }
        delete.setTint(dynamicColors.onPrimaryContainer)
    }

fun interface CollectionItemListener {
    fun onAction(action: Action)

    sealed interface Action
    object Edit : Action
    object Delete : Action
    object View : Action
}

private const val BG_ALPHA = 198