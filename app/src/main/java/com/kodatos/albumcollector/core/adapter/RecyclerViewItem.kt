package com.kodatos.albumcollector.core.adapter

import android.view.View
import androidx.viewbinding.ViewBinding
import com.xwray.groupie.viewbinding.BindableItem

open class RecyclerViewItem<T : ViewBinding>(
    private val layoutID: Int,
    protected val spanCount: Int = 1,
    private val getBinding: (view: View) -> T,
    protected val bindItem: T.(position: Int) -> Unit = {}
) : BindableItem<T>() {

    override fun initializeViewBinding(view: View): T = getBinding(view)

    override fun bind(viewBinding: T, position: Int) {
        viewBinding.bindItem(position)
    }

    override fun getLayout(): Int {
        return layoutID
    }

    override fun getSpanSize(sc: Int, position: Int): Int {
        return sc / spanCount.coerceAtLeast(1)
    }
}

open class DiffRecyclerViewItem<T : ViewBinding>(
    layoutID: Int,
    spanCount: Int = 1,
    private val itemID: Long,
    getBinding: (view: View) -> T,
    bindItem: T.(position: Int) -> Unit = {},
) : RecyclerViewItem<T>(layoutID, spanCount, getBinding = getBinding, bindItem = bindItem) {

    override fun getId(): Long {
        return itemID
    }
}

fun <T : ViewBinding> RecyclerViewItem<T>.fullSizeItem() = listOf(this)
fun emptyRecyclerView(): List<RecyclerViewItem<*>> = emptyList()
