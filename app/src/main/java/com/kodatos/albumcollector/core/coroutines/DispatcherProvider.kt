package com.kodatos.albumcollector.core.coroutines

import kotlinx.coroutines.CoroutineDispatcher

@SuppressWarnings("VariableNaming")
interface DispatcherProvider {

    val MAIN: CoroutineDispatcher
    val IO: CoroutineDispatcher
    val DEFAULT: CoroutineDispatcher
}
