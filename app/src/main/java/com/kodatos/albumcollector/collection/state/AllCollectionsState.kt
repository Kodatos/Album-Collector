package com.kodatos.albumcollector.collection.state

import com.kodatos.albumcollector.collection.models.CollectionModel

sealed interface AllCollectionsState {
    object Loading : AllCollectionsState
    object Empty : AllCollectionsState

    @JvmInline
    value class CollectionsList(val list: List<CollectionModel>) :
        AllCollectionsState,
        List<CollectionModel> by list
}
