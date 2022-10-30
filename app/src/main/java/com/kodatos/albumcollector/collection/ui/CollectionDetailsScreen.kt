package com.kodatos.albumcollector.collection.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.graphics.Insets
import androidx.core.view.updateLayoutParams
import androidx.core.view.updatePadding
import androidx.fragment.app.viewModels
import com.kodatos.albumcollector.R
import com.kodatos.albumcollector.album.adapter.AlbumItemListener
import com.kodatos.albumcollector.album.adapter.albumItem
import com.kodatos.albumcollector.album.models.AlbumModel
import com.kodatos.albumcollector.collection.adapter.collectionHeaderItem
import com.kodatos.albumcollector.collection.state.CollectionAlbumsState
import com.kodatos.albumcollector.collection.state.CollectionDetailsHeaderState
import com.kodatos.albumcollector.collection.viewmodel.CollectionDetailsViewModel
import com.kodatos.albumcollector.core.adapter.*
import com.kodatos.albumcollector.core.ui.*
import com.kodatos.albumcollector.databinding.ScreenViewCollectionBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.roundToInt

@AndroidEntryPoint
class CollectionDetailsScreen : BaseScreen<ScreenViewCollectionBinding>() {

    private val viewModel: CollectionDetailsViewModel by viewModels()

    override fun onInflateBinding(
        layoutInflater: LayoutInflater,
        savedInstanceState: Bundle?
    ): ScreenViewCollectionBinding = ScreenViewCollectionBinding.inflate(layoutInflater)

    override fun onBindingInflated(savedInstanceState: Bundle?) {
        binding.list.setAdapter {
            add(
                viewModel.collectionHeaderState.createAdapterSection(viewLifecycleOwner) {
                    when (it) {
                        is CollectionDetailsHeaderState.Collection ->
                            collectionHeaderItem(it.collectionModel).fullSizeItem()
                        CollectionDetailsHeaderState.Loading -> emptyRecyclerView()
                    }
                }
            )

            add(
                viewModel.collectionAlbumsState.createAdapterSection(viewLifecycleOwner) {
                    when (it) {
                        is CollectionAlbumsState.Albums -> it.map(::albumModelItem)
                        CollectionAlbumsState.Empty -> emptyCollectionItem()
                        is CollectionAlbumsState.Error -> emptyCollectionItem()
                        CollectionAlbumsState.Loading -> listLoaderItem()
                    }
                }
            )
        }
    }

    private fun albumModelItem(albumModel: AlbumModel) = albumItem(albumModel) {
        when (it) {
            AlbumItemListener.Delete -> {}
            AlbumItemListener.Edit -> {}
            is AlbumItemListener.View -> {}
        }
    }


    override fun applySystemBarInsets(insets: Insets) {
        binding.list.updatePadding(bottom = insets.bottom)
        binding.add.updateLayoutParams<ViewGroup.MarginLayoutParams> {
            bottomMargin =
                (insets.bottom + resources.getDimension(R.dimen.fab_margin)).roundToInt()
        }
    }
}