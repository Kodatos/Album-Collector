package com.kodatos.albumcollector.core.adapter

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.xwray.groupie.Section
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

fun section(config: Section.() -> Unit) = Section().apply(config)

fun <T> Flow<T>.createAdapterSection(
    lifecycleOwner: LifecycleOwner,
    safeCollect: Boolean = true,
    getItems: (data: T) -> Collection<RecyclerViewItem<*>>,
): Section = section {
    if (safeCollect) {
        lifecycleOwner.lifecycleScope.launch {
            flowWithLifecycle(lifecycleOwner.lifecycle).collectLatest {
                update(getItems(it))
            }
        }
    } else {
        lifecycleOwner.lifecycleScope.launchWhenStarted {
            collectLatest {
                update(getItems(it))
            }
        }
    }
}