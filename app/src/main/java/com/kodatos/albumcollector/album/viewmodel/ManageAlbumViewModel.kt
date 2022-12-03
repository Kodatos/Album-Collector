package com.kodatos.albumcollector.album.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kodatos.albumcollector.AppModule
import com.kodatos.albumcollector.album.domain.AlbumDomain
import com.kodatos.albumcollector.album.models.AlbumModel
import com.kodatos.albumcollector.album.models.ManageAlbumAction
import com.kodatos.albumcollector.album.ui.ManageAlbumDialogArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import logcat.LogPriority
import logcat.logcat
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class ManageAlbumViewModel @Inject constructor(
    private val albumsDomain: AlbumDomain,
    @Named(AppModule.DEFAULT_DISPATCHER) private val defaultDispatcher: CoroutineDispatcher,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _enableSave = MutableStateFlow<Boolean>(false)
    val enableState: StateFlow<Boolean>
        get() = _enableSave

    private val _initialModel = MutableStateFlow<AlbumModel?>(null)
    val initialModel: StateFlow<AlbumModel?>
        get() = _initialModel

    private val action: ManageAlbumAction =
        ManageAlbumDialogArgs.fromSavedStateHandle(savedStateHandle).action

    init {
        when (action) {
            is ManageAlbumAction.Edit -> {
                viewModelScope.launch(defaultDispatcher) {
                    val album = albumsDomain.getAlbumByID(action.id)
                    if (album != null) {
                        _initialModel.update { album }
                        _enableSave.update { true }
                    } else {
                        logcat(LogPriority.WARN) { "Couldn't find an album for id: ${action.id}" }
                    }
                }
            }
            ManageAlbumAction.New, is ManageAlbumAction.CreateAndAdd -> {
                _enableSave.value = false
            }
        }
    }

    suspend fun saveAlbum(title: String, artist: String, imageUrl: String?, deeplinkUrl: String?) {
        val albumModel = AlbumModel(title, artist, imageUrl, deeplinkUrl)
        when (action) {
            is ManageAlbumAction.Edit -> albumsDomain.updateAlbum(action.id, albumModel)
            ManageAlbumAction.New -> albumsDomain.addAlbum(albumModel)
            is ManageAlbumAction.CreateAndAdd -> {
                albumsDomain.addAlbumWithCollection(albumModel, action.collectionID)
            }
        }
    }

    fun onFieldEntered(title: String?, artist: String?) {
        _enableSave.update { !title.isNullOrBlank() && !artist.isNullOrBlank() }
    }
}