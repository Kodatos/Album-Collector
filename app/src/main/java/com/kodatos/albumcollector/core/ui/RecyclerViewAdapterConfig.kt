package com.kodatos.albumcollector.core.ui

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.Section

fun RecyclerView.setAdapter(config: GroupieAdapter.() -> Unit) {
    val adapter = GroupieAdapter().apply(config)
    val lm = GridLayoutManager(context, adapter.spanCount).apply {
        spanSizeLookup = adapter.spanSizeLookup
    }
    layoutManager = lm
    setAdapter(adapter)
}

fun GroupieAdapter.item(item: RecyclerViewItem<*>) {
    add(item)
}

fun GroupieAdapter.section(section: Section) {
    add(section)
}
