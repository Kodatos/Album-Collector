package com.kodatos.albumcollector.collection.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kodatos.albumcollector.collection.domain.CollectionsDomain
import com.kodatos.albumcollector.collection.models.CollectionModel
import com.kodatos.albumcollector.core.coroutines.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllCollectionsViewmodel @Inject constructor(
    private val collectionsDomain: CollectionsDomain,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    private val _collections = MutableStateFlow<AllCollectionsState>(AllCollectionsState.Loading)
    val collections: StateFlow<AllCollectionsState>
        get() = _collections

    init {
        viewModelScope.launch(dispatcherProvider.DEFAULT) {
            collectionsDomain.getAllCollections().collect {
                _collections.value = if (it.isEmpty())
                    AllCollectionsState.Empty
                else AllCollectionsState.CollectionsList(it)
            }
        }
    }
}

sealed interface AllCollectionsState {
    object Loading : AllCollectionsState
    object Empty : AllCollectionsState

    @JvmInline
    value class CollectionsList(val list: List<CollectionModel>) : AllCollectionsState

}