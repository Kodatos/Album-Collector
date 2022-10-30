package com.kodatos.albumcollector.collection.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kodatos.albumcollector.AppModule
import com.kodatos.albumcollector.collection.domain.CollectionsDomain
import com.kodatos.albumcollector.collection.models.AddCollectionAction
import com.kodatos.albumcollector.collection.models.CollectionModel
import com.kodatos.albumcollector.collection.ui.AddCollectionDialogArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class AddCollectionViewModel @Inject constructor(
    private val collectionsDomain: CollectionsDomain,
    @Named(AppModule.DEFAULT_DISPATCHER) private val defaultDispatcher: CoroutineDispatcher,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _enableSave = MutableStateFlow<Boolean>(false)
    val enableState: StateFlow<Boolean>
        get() = _enableSave

    private val _initialName = MutableStateFlow<String>("")
    private val _initialImage = MutableStateFlow<String>("")
    val initialName: StateFlow<String>
        get() = _initialName
    val initialImage: StateFlow<String>
        get() = _initialImage

    init {
        when (val action = AddCollectionDialogArgs.fromSavedStateHandle(savedStateHandle).action) {
            is AddCollectionAction.Edit -> {
                viewModelScope.launch(defaultDispatcher) {
                    collectionsDomain.getCollectionforId(action.id)?.let {
                        _initialName.value = it.name
                        _initialImage.value = it.imageURL.orEmpty()
                        _enableSave.value = true
                    }
                }
            }
            AddCollectionAction.New -> {
                _enableSave.value = false
            }
        }
    }

    suspend fun saveCollection(name: String, imageUrl: String) {
        when (val action = AddCollectionDialogArgs.fromSavedStateHandle(savedStateHandle).action) {
            is AddCollectionAction.Edit -> collectionsDomain.updateCollection(
                action.id,
                CollectionModel(
                    name,
                    imageUrl
                )
            )

            AddCollectionAction.New -> collectionsDomain.addCollection(
                CollectionModel(
                    name,
                    imageUrl
                )
            )
        }
    }

    fun onTextUpdated(name: String?, imageUrl: String?) {
        _enableSave.update { !name.isNullOrBlank() && !imageUrl.isNullOrBlank() }
    }
}