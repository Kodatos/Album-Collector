package com.kodatos.albumcollector.collection.ui

import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import coil.load
import com.kodatos.albumcollector.collection.viewmodel.AddCollectionViewModel
import com.kodatos.albumcollector.core.coroutines.collectWithLifecycle
import com.kodatos.albumcollector.core.ui.BaseBottomSheetDialog
import com.kodatos.albumcollector.core.ui.textString
import com.kodatos.albumcollector.databinding.FragmentAddCollectionBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddCollectionDialog : BaseBottomSheetDialog<FragmentAddCollectionBinding>() {

    private val viewModel: AddCollectionViewModel by viewModels()

    override fun onInflateBinding(
        layoutInflater: LayoutInflater,
        savedInstanceState: Bundle?
    ) = FragmentAddCollectionBinding.inflate(layoutInflater)

    override fun onBindingInflated(savedInstanceState: Bundle?) {
        with(binding) {
            refreshImage.setOnClickListener {
                imageUrl.text?.let { url ->
                    img.load(url)
                }
            }
            name.addTextChangedListener {
                viewModel.onTextUpdated(name.textString, imageUrl.textString)
            }
            imageUrl.addTextChangedListener {
                viewModel.onTextUpdated(name.textString, imageUrl.textString)
            }

            viewModel.enableState.collectWithLifecycle(viewLifecycleOwner) {
                save.isEnabled = it
            }
            save.setOnClickListener {
                lifecycleScope.launch {
                    viewModel.saveCollection(name.textString!!, imageUrl.textString!!)
                }
            }
        }
    }

}