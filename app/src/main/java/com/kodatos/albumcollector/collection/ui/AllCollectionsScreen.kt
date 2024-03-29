package com.kodatos.albumcollector.collection.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.graphics.Insets
import androidx.core.view.updateLayoutParams
import androidx.core.view.updatePadding
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.kodatos.albumcollector.R
import com.kodatos.albumcollector.collection.adapter.CollectionItemListener
import com.kodatos.albumcollector.collection.adapter.collectionItem
import com.kodatos.albumcollector.collection.models.CollectionModel
import com.kodatos.albumcollector.collection.models.ManageCollectionAction
import com.kodatos.albumcollector.collection.state.AllCollectionsState
import com.kodatos.albumcollector.collection.viewmodel.AllCollectionsViewmodel
import com.kodatos.albumcollector.core.adapter.*
import com.kodatos.albumcollector.core.ui.BaseScreen
import com.kodatos.albumcollector.databinding.ScreenAllCollectionsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.roundToInt

@AndroidEntryPoint
class AllCollectionsScreen : BaseScreen<ScreenAllCollectionsBinding>() {

    private val viewModel: AllCollectionsViewmodel by viewModels()

    override fun onInflateBinding(
        layoutInflater: LayoutInflater,
        savedInstanceState: Bundle?
    ): ScreenAllCollectionsBinding = ScreenAllCollectionsBinding.inflate(layoutInflater)

    override fun onBindingInflated(savedInstanceState: Bundle?) {
        binding.list.setAdapter {
            spanCount = 2

            add(
                viewModel.collections.createAdapterSection(viewLifecycleOwner) {
                    when (it) {
                        is AllCollectionsState.CollectionsList -> {
                            it.map(::collection)
                        }
                        AllCollectionsState.Empty -> emptyRecyclerView()
                        AllCollectionsState.Loading -> listLoaderItem()
                    }
                }
            )
        }
        binding.add.setOnClickListener {
            findNavController().navigate(
                AllCollectionsScreenDirections.addCollection(
                    ManageCollectionAction.New
                )
            )
        }
    }

    private fun collection(model: CollectionModel) = collectionItem(model) { action ->
        when (action) {
            CollectionItemListener.Delete -> {
                viewModel.deleteCollection(model)
            }
            CollectionItemListener.Edit -> {
                findNavController().navigate(
                    AllCollectionsScreenDirections.addCollection(
                        ManageCollectionAction.Edit(model.id)
                    )
                )
            }
            CollectionItemListener.View -> {
                findNavController().navigate(AllCollectionsScreenDirections.viewCollection(model.id))
            }
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
