package com.kodatos.albumcollector.collection.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kodatos.albumcollector.AppModule
import com.kodatos.albumcollector.R
import com.kodatos.albumcollector.collection.domain.CollectionsDomain
import com.kodatos.albumcollector.collection.state.CollectionAlbumsState
import com.kodatos.albumcollector.collection.state.CollectionDetailsHeaderState
import com.kodatos.albumcollector.collection.ui.CollectionDetailsScreenArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class CollectionDetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val collectionsDomain: CollectionsDomain,
    @Named(AppModule.IO_DISPATCHER) private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _collectionHeaderState =
        MutableStateFlow<CollectionDetailsHeaderState>(CollectionDetailsHeaderState.Loading)
    val collectionHeaderState = _collectionHeaderState.asStateFlow()

    private val _collectionAlbumsState =
        MutableStateFlow<CollectionAlbumsState>(CollectionAlbumsState.Loading)
    val collectionAlbumsState = _collectionAlbumsState.asStateFlow()

    val collectionID
        get() = CollectionDetailsScreenArgs.fromSavedStateHandle(savedStateHandle).id

    init {
        viewModelScope.launch(ioDispatcher) {
            collectionsDomain.getCollectionforId(collectionID)?.let {
                _collectionHeaderState.value = CollectionDetailsHeaderState.Collection(it)
            }
        }
        viewModelScope.launch(ioDispatcher) {
            collectionsDomain.getAlbumsForCollection(collectionID).catch { e ->
                if (e !is CancellationException) {
                    _collectionAlbumsState.update { CollectionAlbumsState.Error(R.string.collection_details_error) }
                }
            }.collect { albums ->
                _collectionAlbumsState.update {
                    if (albums.isEmpty()) CollectionAlbumsState.Empty else CollectionAlbumsState.Albums(
                        albums
                    )
                }
            }
        }
    }

    fun deleteAlbum(albumID: Long) {
        viewModelScope.launch(ioDispatcher) {
            collectionsDomain.deleteAlbumFromCollection(collectionID, albumID)
        }
    }
}