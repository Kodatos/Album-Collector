package com.kodatos.albumcollector.collection.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.kodatos.albumcollector.R
import com.kodatos.albumcollector.collection.models.ManageCollectionAction
import com.kodatos.albumcollector.collection.viewmodel.ManageCollectionViewModel
import com.kodatos.albumcollector.core.coroutines.collectLatestLifecycle
import com.kodatos.albumcollector.core.ui.*
import com.kodatos.albumcollector.databinding.ScreenAddCollectionBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ManageCollectionDialog : BaseBottomSheetDialog<ScreenAddCollectionBinding>() {

    private val viewModel: ManageCollectionViewModel by viewModels()
    private val args: ManageCollectionDialogArgs by navArgs()

    override fun onInflateBinding(
        layoutInflater: LayoutInflater,
        savedInstanceState: Bundle?
    ) = ScreenAddCollectionBinding.inflate(layoutInflater)

    override fun onBindingInflated(savedInstanceState: Bundle?) {
        with(binding) {
            viewModel.initialName.filterNot { it.isEmpty() }
                .collectLatestLifecycle(viewLifecycleOwner) {
                    name.setText(it)
                }
            img.post {
                viewModel.initialImage.filterNot { it.isEmpty() }
                    .collectLatestLifecycle(viewLifecycleOwner) {
                        loadUrl(it)
                        imageUrl.setText(it, TextView.BufferType.EDITABLE)
                    }
            }

            header.setText(
                when (args.action) {
                    is ManageCollectionAction.Edit -> R.string.edit_collections_header
                    ManageCollectionAction.New -> R.string.add_collections_header
                }
            )
            imgEl.setEndIconOnClickListener {
                loadUrl(imageUrl.textString)
            }
            name.addTextChangedListener {
                viewModel.onTextUpdated(name.textString, imageUrl.textString)
            }
            imageUrl.addTextChangedListener {
                viewModel.onTextUpdated(name.textString, imageUrl.textString)
            }

            viewModel.enableState.collectLatestLifecycle(viewLifecycleOwner) {
                save.isEnabled = it
            }

            save.setOnClickListener {
                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.saveCollection(name.textString, imageUrl.textString)
                    dismiss()
                }
            }
        }
    }

    private fun loadUrl(url: String) {
        with(binding.img) {
            binding.imgLoader.isVisible = true
            loadUrlOrTintPlaceholder(
                url,
                R.drawable.ic_album,
                context.dynamicColors.secondary,
                context.resources.getDimension(R.dimen.placeholder_padding).toInt(),
            ) {
                binding.imgLoader.isVisible = false
            }
        }
    }
}