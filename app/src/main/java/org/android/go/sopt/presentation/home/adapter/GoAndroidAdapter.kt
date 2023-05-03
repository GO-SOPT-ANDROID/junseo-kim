package org.android.go.sopt.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.android.go.sopt.data.model.PartMember
import org.android.go.sopt.databinding.ItemGoAndroidBinding
import org.android.go.sopt.databinding.ItemGoAndroidHeaderBinding

class GoAndroidAdapter : ListAdapter<PartMember, RecyclerView.ViewHolder>(diffUtil) {
    override fun getItemViewType(position: Int): Int {
        return if (position == 0) HEADER
        else CONTENT
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == HEADER) {
            val binding = ItemGoAndroidHeaderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            HeaderViewHolder(binding)
        } else {
            val binding =
                ItemGoAndroidBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            GoAndroidViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position == HEADER)
            (holder as HeaderViewHolder).onBind(currentList[position])
        else
            (holder as GoAndroidViewHolder).onBind(currentList[position])
    }

    class HeaderViewHolder(private val binding: ItemGoAndroidHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(header: PartMember) {
            with(binding) {
                ivGoAndroid.setImageResource(header.imageResourceContent)
                tvGoAndroidHeaderTitle.text = header.stringContent
            }
        }
    }

    class GoAndroidViewHolder(private val binding: ItemGoAndroidBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(partMember: PartMember) {
            with(binding) {
                ivGoAndroid.setImageResource(partMember.imageResourceContent)
                tvGoAndroidName.text = partMember.stringContent
            }
        }
    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<PartMember>() {

            override fun areItemsTheSame(oldItem: PartMember, newItem: PartMember): Boolean =
                oldItem === newItem

            override fun areContentsTheSame(oldItem: PartMember, newItem: PartMember): Boolean =
                oldItem == newItem

        }

        private const val HEADER = 0
        private const val CONTENT = 1
    }
}