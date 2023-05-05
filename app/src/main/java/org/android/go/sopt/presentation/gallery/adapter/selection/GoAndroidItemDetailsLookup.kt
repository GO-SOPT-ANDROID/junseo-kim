package org.android.go.sopt.presentation.gallery.adapter.selection

import android.view.MotionEvent
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.widget.RecyclerView
import org.android.go.sopt.presentation.gallery.adapter.GoAndroidAdapter

class GoAndroidItemDetailsLookup(private val recyclerView: RecyclerView) :
    ItemDetailsLookup<Long>() {
    override fun getItemDetails(e: MotionEvent): ItemDetails<Long>? {
        val view = recyclerView.findChildViewUnder(e.x, e.y) ?: return null
        val viewHolder = recyclerView.getChildViewHolder(view)
        return if (viewHolder.itemViewType == GoAndroidAdapter.HEADER) {
            (viewHolder as GoAndroidAdapter.HeaderViewHolder).getItemDetails()
        } else {
            (viewHolder as GoAndroidAdapter.GoAndroidViewHolder).getItemDetails()
        }
    }

}