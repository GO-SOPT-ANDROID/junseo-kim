package org.android.go.sopt.presentation.gallery.adapter.selection

import androidx.recyclerview.selection.ItemKeyProvider
import androidx.recyclerview.widget.RecyclerView

class SelectionItemKeyProvider(
    private val recyclerView: RecyclerView,
) : ItemKeyProvider<Long>(SCOPE_MAPPED) {
    override fun getKey(position: Int): Long {
        val holder = recyclerView.findViewHolderForAdapterPosition(position)
        return holder?.itemId ?: RecyclerView.NO_ID
    }

    override fun getPosition(key: Long): Int {
        val holder = recyclerView.findViewHolderForItemId(key)
        return holder?.adapterPosition ?: RecyclerView.NO_POSITION
    }
}