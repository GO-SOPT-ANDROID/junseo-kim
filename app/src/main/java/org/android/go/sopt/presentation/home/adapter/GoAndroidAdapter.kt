package org.android.go.sopt.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.android.go.sopt.data.model.PartMember
import org.android.go.sopt.databinding.ItemGoAndroidBinding

class GoAndroidAdapter : ListAdapter<PartMember, GoAndroidAdapter.GoAndroidViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoAndroidViewHolder {
        val binding =
            ItemGoAndroidBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GoAndroidViewHolder(binding)
    }

    class GoAndroidViewHolder(private val binding: ItemGoAndroidBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(partMember: PartMember) {
            with(binding) {
                ivGoAndroid.setImageResource(partMember.memberImage)
                tvGoAndroidName.text = partMember.memberName
            }
        }
    }

    override fun onBindViewHolder(holder: GoAndroidViewHolder, position: Int) {
        holder.onBind(currentList[position])
    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<PartMember>() {

            override fun areItemsTheSame(oldItem: PartMember, newItem: PartMember): Boolean =
                oldItem === newItem

            override fun areContentsTheSame(oldItem: PartMember, newItem: PartMember): Boolean =
                oldItem == newItem

        }
    }
}