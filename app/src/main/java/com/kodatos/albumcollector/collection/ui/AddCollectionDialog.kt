package com.kodatos.albumcollector.collection.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import coil.load
import com.kodatos.albumcollector.R
import com.kodatos.albumcollector.collection.models.AddCollectionAction
import com.kodatos.albumcollector.collection.viewmodel.AddCollectionViewModel
import com.kodatos.albumcollector.core.coroutines.collectLatestLifecycle
import com.kodatos.albumcollector.core.ui.BaseBottomSheetDialog
import com.kodatos.albumcollector.core.ui.dynamicColors
import com.kodatos.albumcollector.core.ui.setTint
import com.kodatos.albumcollector.core.ui.textString
import com.kodatos.albumcollector.databinding.FragmentAddCollectionBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddCollectionDialog : BaseBottomSheetDialog<FragmentAddCollectionBinding>() {

    private val viewModel: AddCollectionViewModel by viewModels()
    private val args: AddCollectionDialogArgs by navArgs()

    override fun onInflateBinding(
        layoutInflater: LayoutInflater,
        savedInstanceState: Bundle?
    ) = FragmentAddCollectionBinding.inflate(layoutInflater)

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
                    is AddCollectionAction.Edit -> R.string.edit_collections_header
                    AddCollectionAction.New -> R.string.add_collections_header
                }
            )
            imgEl.setEndIconOnClickListener {
                imageUrl.textString?.let { url ->
                    loadUrl(url)
                }
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
                lifecycleScope.launch {
                    viewModel.saveCollection(name.textString!!, imageUrl.textString!!)
                    dismiss()
                }
            }
        }
    }

    private fun loadUrl(url: String) {
        with(binding.img) {
            binding.imgLoader.isVisible = true
            load(url) {
                error(R.drawable.ic_album)
                placeholder(R.drawable.ic_album)
                listener(
                    onError = { _, _ ->
                        binding.imgLoader.isVisible = false
                        val colors = context.dynamicColors
                        setTint(colors.secondary)
                        val padding = resources.getDimension(R.dimen.placeholder_padding).toInt()
                        setContentPadding(padding, padding, padding, padding)
                    },
                    onSuccess = { _, _ ->
                        binding.imgLoader.isVisible = false
                        setContentPadding(0, 0, 0, 0)
                        imageTintList = null
                    }
                )
            }
        }
    }

}