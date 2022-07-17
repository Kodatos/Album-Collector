package com.kodatos.albumcollector.core.ui

import android.view.View
import androidx.viewbinding.ViewBinding
import com.xwray.groupie.viewbinding.BindableItem

open class RecyclerViewItem<T : ViewBinding>(
    private val layoutID: Int,
    private val spanCount: Int = 1,
    private val getBinding: (view: View) -> T,
    private val bindItem: T.(position: Int) -> Unit
) : BindableItem<T>() {

    override fun initializeViewBinding(view: View): T = getBinding(view)

    override fun bind(viewBinding: T, position: Int) {
        viewBinding.bindItem(position)
    }

    override fun getLayout(): Int {
        return layoutID
    }

    override fun getSpanSize(sc: Int, position: Int): Int {
        return sc/spanCount.coerceAtLeast(1)
    }
}

open class DiffRecyclerViewItem<T : ViewBinding>(
    layoutID: Int,
    spanCount: Int = 1,
    getBinding: (view: View) -> T,
    bindItem: T.(position: Int) -> Unit,
    private val itemID: Long,
) : RecyclerViewItem<T>(layoutID, spanCount, getBinding = getBinding, bindItem = bindItem) {

    override fun getId(): Long {
        return itemID
    }
}


