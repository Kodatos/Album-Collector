package com.kodatos.albumcollector.collection.viewmodel

import androidx.lifecycle.ViewModel
import com.kodatos.albumcollector.collection.domain.CollectionsDomain
import com.kodatos.albumcollector.core.coroutines.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ViewCollectionViewModel @Inject constructor(
    private val collectionsDomain: CollectionsDomain,
    private val dispatcherProvider: DispatcherProvider
): ViewModel() {

}