package com.kodatos.albumcollector.collection.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed interface AddCollectionAction : Parcelable {
    @Parcelize
    object New : AddCollectionAction

    @Parcelize
    class Edit(val id: Long) : AddCollectionAction
}
