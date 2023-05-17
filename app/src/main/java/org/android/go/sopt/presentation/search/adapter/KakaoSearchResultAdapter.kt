package org.android.go.sopt.presentation.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import org.android.go.sopt.data.remote.model.ResponseKakaoSearchDto.Document
import org.android.go.sopt.databinding.ItemKakaoSearchResultBinding
import org.android.go.sopt.presentation.search.adapter.KakaoSearchResultAdapter.KakaoSearchResultViewHolder
import org.android.go.sopt.util.DiffUtil

class KakaoSearchResultAdapter() :
    ListAdapter<Document, KakaoSearchResultViewHolder>(DiffUtil<Document> { oldItem, newItem ->
        oldItem == newItem
    }) {
    lateinit var binding: ItemKakaoSearchResultBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KakaoSearchResultViewHolder {
        binding =
            ItemKakaoSearchResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return KakaoSearchResultViewHolder(binding)
    }

    override fun onBindViewHolder(holder: KakaoSearchResultViewHolder, position: Int) {
        holder.onBind(currentList[position])
    }

    class KakaoSearchResultViewHolder(private val binding: ItemKakaoSearchResultBinding) :
        ViewHolder(binding.root) {
        fun onBind(item: Document) {
            with(binding) {
                tvKakaoSearchResultTitle.text =
                    HtmlCompat.fromHtml(item.title, HtmlCompat.FROM_HTML_MODE_LEGACY)
                tvKakaoSearchResultUrl.text = item.url
            }
        }
    }
}