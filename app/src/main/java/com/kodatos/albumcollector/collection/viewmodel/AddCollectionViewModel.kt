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
class AddCollectionViewModel @Inject constructor(
    private val collectionsDomain: CollectionsDomain,
    private val dispatcherProvider: DispatcherProvider,
): ViewModel() {

    private val _enableSave = MutableStateFlow<Boolean>(false)
    val enableState: StateFlow<Boolean>
        get() = _enableSave

    suspend fun saveCollection(name: String, imageUrl: String) {
        collectionsDomain.addCollection(CollectionModel(name, imageUrl))
    }

    fun onTextUpdated(name: String?, imageUrl: String?) {
        _enableSave.value = !name.isNullOrBlank() && !imageUrl.isNullOrBlank()
    }
}