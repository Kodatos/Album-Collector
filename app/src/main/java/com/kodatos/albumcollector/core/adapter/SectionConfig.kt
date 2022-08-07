package com.kodatos.albumcollector.core.adapter

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.xwray.groupie.Section
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

fun section(config: Section.() -> Unit) = Section().apply(config)

fun <T> Flow<T>.createSection(
    lifecycleOwner: LifecycleOwner,
    getItems: (data: T) -> List<RecyclerViewItem<*>>
) = section {
    lifecycleOwner.lifecycleScope.launch {
        flowWithLifecycle(lifecycleOwner.lifecycle).collect {
            update(getItems(it))
        }
    }
}