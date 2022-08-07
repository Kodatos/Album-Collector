package com.kodatos.albumcollector.core.coroutines

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

fun <T> Flow<T>.collectWithLifecycle(lifecycleOwner: LifecycleOwner, collector: (T) -> Unit) {
    lifecycleOwner.lifecycleScope.launch {
        flowWithLifecycle(lifecycleOwner.lifecycle).collect(collector)
    }
}