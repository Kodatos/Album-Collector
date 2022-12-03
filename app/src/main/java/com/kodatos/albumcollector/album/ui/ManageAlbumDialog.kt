package com.kodatos.albumcollector.album.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.kodatos.albumcollector.R
import com.kodatos.albumcollector.album.models.ManageAlbumAction
import com.kodatos.albumcollector.album.viewmodel.ManageAlbumViewModel
import com.kodatos.albumcollector.core.coroutines.collectLatestLifecycle
import com.kodatos.albumcollector.core.ui.*
import com.kodatos.albumcollector.databinding.ScreenAddAlbumBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ManageAlbumDialog : BaseBottomSheetDialog<ScreenAddAlbumBinding>() {

    private val viewmodel: ManageAlbumViewModel by viewModels()
    private val navArgs: ManageAlbumDialogArgs by navArgs()

    override fun onInflateBinding(
        layoutInflater: LayoutInflater,
        savedInstanceState: Bundle?
    ): ScreenAddAlbumBinding = ScreenAddAlbumBinding.inflate(layoutInflater)

    override fun onBindingInflated(savedInstanceState: Bundle?) {
        with(binding) {
            viewmodel.initialModel.filterNotNull()
                .collectLatestLifecycle(viewLifecycleOwner) { album ->
                    title.setText(album.title)
                    artist.setText(album.artist)
                    linkUrl.setText(album.deepLink)
                    img.post {
                        album.imageURL?.let { loadUrl(it) }
                        imageUrl.setText(album.imageURL, TextView.BufferType.EDITABLE)
                    }
                }
            viewmodel.enableState.collectLatestLifecycle(viewLifecycleOwner) {
                save.isEnabled = it
            }
            header.setText(
                when (navArgs.action) {
                    is ManageAlbumAction.Edit -> R.string.edit_album_header
                    ManageAlbumAction.New, is ManageAlbumAction.CreateAndAdd -> R.string.add_album_header
                }
            )
            title.addTextChangedListener {
                viewmodel.onFieldEntered(title.textString, artist.textString)
            }

            artist.addTextChangedListener {
                viewmodel.onFieldEntered(title.textString, artist.textString)
            }

            imgEl.setEndIconOnClickListener {
                loadUrl(imageUrl.textString)
            }

            save.setOnClickListener {
                viewLifecycleOwner.lifecycleScope.launch {
                    viewmodel.saveAlbum(
                        title.textString,
                        artist.textString,
                        imageUrl.textOrNull,
                        linkUrl.textOrNull,
                    )
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