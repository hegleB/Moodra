package com.quere.presenation.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.quere.domain.model.tv.TVshow
import com.quere.presenation.R
import com.quere.presenation.base.BasePagingAdapter
import com.quere.presenation.base.BaseViewHolder
import com.quere.presenation.databinding.ItemTvSearchBinding

class TVSearchPagingAdapter(private val ItemClick : (TVshow) -> Unit) : BasePagingAdapter<TVshow>(itemCallback) {

    companion object {
        private val itemCallback = object : DiffUtil.ItemCallback<TVshow>() {
            override fun areItemsTheSame(oldItem: TVshow, newItem: TVshow): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: TVshow, newItem: TVshow): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position == itemCount){
            return R.layout.search_detail_load
        }else {
            return R.layout.item_search_all
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<out ViewDataBinding, TVshow> {
        return TVHolder(
            ItemTvSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            ItemClick
        )
    }

    inner class TVHolder(binding: ItemTvSearchBinding, private val itemClickListener: (TVshow) -> Unit)
        : BaseViewHolder<ItemTvSearchBinding, TVshow>(binding) {

        init {
            binding.apply {
                itemView.setOnClickListener {
                    tv?.run {
                        itemClickListener(this)
                    }
                }
            }
        }

        override fun bind(element: TVshow) {
            super.bind(element)
            binding.tv = element

        }
    }
}