package org.android.go.sopt.util

import androidx.recyclerview.widget.DiffUtil

class DiffUtil<T : Any>(
    val areContentsSame: (T, T) -> Boolean,
) : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean =
        oldItem === newItem

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean =
        areContentsSame(oldItem, newItem)
}