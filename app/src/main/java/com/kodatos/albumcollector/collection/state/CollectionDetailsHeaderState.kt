package com.kodatos.albumcollector.collection.state

import com.kodatos.albumcollector.collection.models.CollectionModel

sealed interface CollectionDetailsHeaderState {
    object Loading : CollectionDetailsHeaderState

    class Collection(val collectionModel: CollectionModel) : CollectionDetailsHeaderState
}