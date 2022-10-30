package com.kodatos.albumcollector.collection.state

import androidx.annotation.StringRes
import com.kodatos.albumcollector.album.models.AlbumModel

sealed interface CollectionAlbumsState {
    object Loading : CollectionAlbumsState
    object Empty : CollectionAlbumsState

    class Error(@StringRes val reason: Int) : CollectionAlbumsState

    @JvmInline
    value class Albums(val list: List<AlbumModel>) : CollectionAlbumsState, List<AlbumModel> by list
}