package com.kodatos.albumcollector.core.coroutines

import kotlinx.coroutines.CoroutineDispatcher

interface DispatcherProvider {

    val MAIN: CoroutineDispatcher
    val IO: CoroutineDispatcher
    val DEFAULT: CoroutineDispatcher

}
