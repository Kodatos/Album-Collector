package com.kodatos.albumcollector.collection.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.core.view.updatePadding
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.kodatos.albumcollector.R
import com.kodatos.albumcollector.collection.models.AddCollectionAction
import com.kodatos.albumcollector.collection.models.CollectionModel
import com.kodatos.albumcollector.collection.viewmodel.AllCollectionsState
import com.kodatos.albumcollector.collection.viewmodel.AllCollectionsViewmodel
import com.kodatos.albumcollector.core.adapter.headerItem
import com.kodatos.albumcollector.core.adapter.listLoaderItem
import com.kodatos.albumcollector.core.adapter.setAdapter
import com.kodatos.albumcollector.core.ui.BaseScreen
import com.kodatos.albumcollector.core.adapter.createSection
import com.kodatos.albumcollector.core.ui.dp
import com.kodatos.albumcollector.databinding.FragmentAllCollectionsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.roundToInt

@AndroidEntryPoint
class AllCollectionsScreen : BaseScreen<FragmentAllCollectionsBinding>() {

    private val viewModel: AllCollectionsViewmodel by viewModels()

    override fun onInflateBinding(
        layoutInflater: LayoutInflater,
        savedInstanceState: Bundle?
    ): FragmentAllCollectionsBinding = FragmentAllCollectionsBinding.inflate(layoutInflater)

    override fun onBindingInflated(savedInstanceState: Bundle?) {
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { _, wi ->
            val insets = wi.getInsets(WindowInsetsCompat.Type.systemBars())
            binding.list.updatePadding(bottom = insets.bottom)
            binding.add.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                bottomMargin =
                    (insets.bottom + resources.getDimension(R.dimen.fab_margin)).roundToInt()
            }
            WindowInsetsCompat.CONSUMED
        }
        binding.list.setAdapter {
            spanCount = 2
            add(headerItem(getString(R.string.collections_header), 8.dp, 16.dp, 16.dp))
            add(viewModel.collections.createSection(viewLifecycleOwner) {
                when (it) {
                    is AllCollectionsState.CollectionsList -> {
                        it.list.map(::collectionItem)
                    }
                    AllCollectionsState.Empty -> listOf()
                    AllCollectionsState.Loading -> listOf(listLoaderItem())
                }
            })
        }
        binding.add.setOnClickListener {
            findNavController().navigate(
                AllCollectionsScreenDirections.addCollection(
                    AddCollectionAction.New
                )
            )
        }
    }

    private fun collectionItem(model: CollectionModel) = collectionItem(model) { action ->
        when (action) {
            CollectionItemListener.Delete -> {}
            CollectionItemListener.Edit -> {
                findNavController().navigate(
                    AllCollectionsScreenDirections.addCollection(
                        AddCollectionAction.Edit(model.id)
                    )
                )
            }
            CollectionItemListener.View -> {}
        }
    }

}