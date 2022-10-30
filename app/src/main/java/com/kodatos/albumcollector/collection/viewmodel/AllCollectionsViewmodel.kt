package com.kodatos.albumcollector.collection.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kodatos.albumcollector.AppModule
import com.kodatos.albumcollector.collection.domain.CollectionsDomain
import com.kodatos.albumcollector.collection.models.CollectionModel
import com.kodatos.albumcollector.collection.state.AllCollectionsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class AllCollectionsViewmodel @Inject constructor(
    private val collectionsDomain: CollectionsDomain,
    @Named(AppModule.IO_DISPATCHER) private val ioDispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _collections = MutableStateFlow<AllCollectionsState>(AllCollectionsState.Loading)
    val collections: StateFlow<AllCollectionsState>
        get() = _collections

    init {
        viewModelScope.launch(ioDispatcher) {
            collectionsDomain.getAllCollections().collect {
                _collections.update { _ ->
                    if (it.isEmpty()) {
                        AllCollectionsState.Empty
                    } else AllCollectionsState.CollectionsList(it)
                }
            }
        }
    }

    fun deleteCollection(collectionModel: CollectionModel) {
        viewModelScope.launch(ioDispatcher) {
            collectionsDomain.deleteCollection(collectionModel)
        }
    }
}
