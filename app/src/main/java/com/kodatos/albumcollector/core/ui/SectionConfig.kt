package com.kodatos.albumcollector.core.ui

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.xwray.groupie.Section
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

fun section(config: Section.() -> Unit) = Section().apply(config)

fun <T> LifecycleOwner.createSection(flow: Flow<T>, getItems: (data: T) -> List<RecyclerViewItem<*>>) = section {
    lifecycleScope.launch {
        flow.flowWithLifecycle(lifecycle).collect {
            update(getItems(it))
        }
    }
}