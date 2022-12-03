package com.kodatos.albumcollector.album.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed interface ManageAlbumAction : Parcelable {
    @Parcelize
    object New : ManageAlbumAction

    @Parcelize
    class Edit(val id: Long) : ManageAlbumAction

    @Parcelize
    class CreateAndAdd(val collectionID: Long) : ManageAlbumAction
}
