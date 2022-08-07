package com.kodatos.albumcollector.collection.ui

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.kodatos.albumcollector.R
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

@AndroidEntryPoint
class AllCollectionsScreen : BaseScreen<FragmentAllCollectionsBinding>() {

    private val viewModel: AllCollectionsViewmodel by viewModels()

    override fun onInflateBinding(
        layoutInflater: LayoutInflater,
        savedInstanceState: Bundle?
    ): FragmentAllCollectionsBinding  = FragmentAllCollectionsBinding.inflate(layoutInflater)

    override fun onBindingInflated(savedInstanceState: Bundle?) {
        binding.list.setAdapter {
            add(headerItem(getString(R.string.collections_header), 16.dp))
            viewModel.collections.createSection(viewLifecycleOwner) {
                when (it) {
                    is AllCollectionsState.CollectionsList -> {
                        it.map { model ->
                            collectionItem(model) {

                            }
                        }
                    }
                    AllCollectionsState.Empty -> listOf()
                    AllCollectionsState.Loading -> listOf(listLoaderItem())
                }
            }
        }
        binding.add.setOnClickListener {
            findNavController().navigate(R.id.add_collection)
        }
    }

}