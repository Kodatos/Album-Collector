package com.kodatos.albumcollector.collection.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.graphics.Insets
import androidx.core.view.updateLayoutParams
import androidx.core.view.updatePadding
import androidx.fragment.app.viewModels
import com.kodatos.albumcollector.R
import com.kodatos.albumcollector.collection.viewmodel.ViewCollectionViewModel
import com.kodatos.albumcollector.core.adapter.setAdapter
import com.kodatos.albumcollector.core.ui.BaseScreen
import com.kodatos.albumcollector.databinding.ScreenViewCollectionBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.roundToInt

@AndroidEntryPoint
class CollectionDetailsScreen: BaseScreen<ScreenViewCollectionBinding>() {

    private val viewModel: ViewCollectionViewModel by viewModels()

    override fun onInflateBinding(
        layoutInflater: LayoutInflater,
        savedInstanceState: Bundle?
    ): ScreenViewCollectionBinding = ScreenViewCollectionBinding.inflate(layoutInflater)

    override fun onBindingInflated(savedInstanceState: Bundle?) {
        binding.list.setAdapter {

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