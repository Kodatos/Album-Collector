package com.kodatos.albumcollector.collection.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed interface ManageCollectionAction : Parcelable {
    @Parcelize
    object New : ManageCollectionAction

    @Parcelize
    class Edit(val id: Long) : ManageCollectionAction
}
