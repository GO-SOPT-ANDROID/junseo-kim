package org.android.go.sopt.presentation.gallery.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.android.go.sopt.R
import org.android.go.sopt.data.local.model.PartMember
import org.android.go.sopt.databinding.ItemGoAndroidBinding
import org.android.go.sopt.databinding.ItemGoAndroidHeaderBinding
import org.android.go.sopt.util.DiffUtil
import org.android.go.sopt.util.extensions.makeToastMessage

class GoAndroidAdapter :
    ListAdapter<PartMember, RecyclerView.ViewHolder>(DiffUtil<PartMember> { oldItem, newItem ->
        oldItem == newItem
    }) {
    private lateinit var tracker: SelectionTracker<Long>

    init {
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) HEADER
        else CONTENT
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == HEADER) {
            val binding = ItemGoAndroidHeaderBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            HeaderViewHolder(binding)
        } else {
            val binding =
                ItemGoAndroidBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            GoAndroidViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position == HEADER) {
            (holder as HeaderViewHolder).run {
                onBind(currentList[position], tracker)
            }
        } else {
            (holder as GoAndroidViewHolder).run {
                onBind(currentList[position], tracker)
            }
        }

    }

    class HeaderViewHolder(private val binding: ItemGoAndroidHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(header: PartMember, tracker: SelectionTracker<Long>) {
            with(binding) {
                ivGoAndroid.setImageResource(header.imageResourceContent)
                tvGoAndroidHeaderTitle.text = header.stringContent
                root.isActivated = tracker.isSelected(itemId)
                if (tracker.isSelected(adapterPosition.toLong())) {
                    root.context.makeToastMessage(root.context.getString(R.string.hello_go_android))
                } else {
                    binding.root.setBackgroundResource(R.color.transparent)
                }
            }
        }

        fun getItemDetails(): ItemDetailsLookup.ItemDetails<Long> =
            object : ItemDetailsLookup.ItemDetails<Long>() {
                override fun getPosition(): Int = adapterPosition
                override fun getSelectionKey(): Long = itemId
            }
    }

    class GoAndroidViewHolder(private val binding: ItemGoAndroidBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(partMember: PartMember, tracker: SelectionTracker<Long>) {
            with(binding) {
                ivGoAndroid.setImageResource(partMember.imageResourceContent)
                tvGoAndroidName.text = partMember.stringContent
            }
            if (tracker.isSelected(adapterPosition.toLong())) {
                binding.root.setBackgroundResource(R.color.purple_200)
            } else {
                binding.root.setBackgroundResource(R.color.transparent)
            }
        }

        fun getItemDetails(): ItemDetailsLookup.ItemDetails<Long> =
            object : ItemDetailsLookup.ItemDetails<Long>() {
                override fun getPosition(): Int = adapterPosition
                override fun getSelectionKey(): Long = itemId
            }
    }

    fun setSelectionTracker(tracker: SelectionTracker<Long>) {
        this.tracker = tracker
    }

    fun getSelectionTracker(): SelectionTracker<Long> = tracker

    companion object {
        const val HEADER = 0
        const val CONTENT = 1
    }
}