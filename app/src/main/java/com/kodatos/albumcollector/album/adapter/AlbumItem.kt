package com.kodatos.albumcollector.album.adapter

import androidx.core.view.isVisible
import androidx.transition.TransitionManager
import com.google.android.material.transition.MaterialSharedAxis
import com.kodatos.albumcollector.R
import com.kodatos.albumcollector.album.models.AlbumModel
import com.kodatos.albumcollector.core.adapter.DiffRecyclerViewItem
import com.kodatos.albumcollector.core.ui.context
import com.kodatos.albumcollector.core.ui.dynamicColors
import com.kodatos.albumcollector.core.ui.loadUrlOrTintPlaceholder
import com.kodatos.albumcollector.databinding.AlbumItemBinding

private class AlbumItem(
    private val albumModel: AlbumModel,
    private val listener: AlbumItemListener
) : DiffRecyclerViewItem<AlbumItemBinding>(
    R.layout.album_item,
    spanCount = 2,
    itemID = albumModel.id,
    getBinding = AlbumItemBinding::bind,
) {
    override fun bind(viewBinding: AlbumItemBinding, position: Int) {
        with(viewBinding) {
            editLayout.isVisible = false
            img.loadUrlOrTintPlaceholder(
                albumModel.imageURL,
                R.drawable.ic_album,
                context.dynamicColors.secondary,
                context.resources.getDimension(R.dimen.placeholder_padding).toInt(),
            )
            albumName.text = albumModel.title
            artistName.text = albumModel.artist
            parentLayout.setOnLongClickListener {
                detailsEditTransition(this, !detailsLayout.isVisible)
                true
            }
            close.setOnClickListener {
                detailsEditTransition(this, false)
            }
            edit.setOnClickListener {
                listener.onAction(AlbumItemListener.Edit)
            }
            delete.setOnClickListener {
                listener.onAction(AlbumItemListener.Delete)
            }
            albumModel.deepLink?.let {
                detailsLayout.setOnClickListener {
                    listener.onAction(AlbumItemListener.View(albumModel.deepLink))
                }
            }
        }
    }

    private fun detailsEditTransition(binding: AlbumItemBinding, show: Boolean) {
        TransitionManager.beginDelayedTransition(
            binding.root,
            MaterialSharedAxis(MaterialSharedAxis.X, show)
        )
        binding.detailsLayout.isVisible = !show
        binding.editLayout.isVisible = show
    }
}

fun albumModel(
    albumModel: AlbumModel,
    listener: AlbumItemListener
): DiffRecyclerViewItem<AlbumItemBinding> = AlbumItem(albumModel, listener)

fun interface AlbumItemListener {
    fun onAction(action: Action)

    sealed interface Action
    object Edit : Action
    object Delete : Action
    class View(val deeplink: String) : Action
}