package org.android.go.sopt.presentation.home.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import org.android.go.sopt.data.remote.model.ResponseReqresDto
import org.android.go.sopt.databinding.ItemPagerBinding

class ViewPagerAdapter(_itemList: List<ResponseReqresDto.User>?) :
    RecyclerView.Adapter<ViewPagerAdapter.PagerViewHolder>() {
    lateinit var binding: ItemPagerBinding
    private var itemList: List<ResponseReqresDto.User> =
        _itemList ?: listOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        binding = ItemPagerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PagerViewHolder(binding)
    }

    class PagerViewHolder(private val binding: ItemPagerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: ResponseReqresDto.User) {
            binding.ivPager.load(item.avatar)
        }
    }


    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.onBind(itemList[position])
    }

    override fun getItemCount(): Int = itemList.size

    fun setItemList(itemList: List<ResponseReqresDto.User>) {
        this.itemList = itemList
        notifyDataSetChanged()
    }
}