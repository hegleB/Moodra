package com.quere.presenation.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.quere.domain.model.tv.TVshow
import com.quere.presenation.base.BasePagingAdapter
import com.quere.presenation.base.BaseViewHolder
import com.quere.presenation.databinding.ItemTvshowBinding

class TVPagingAdapter(private val ItemClick : (TVshow) -> Unit) : BasePagingAdapter<TVshow>(itemCallback) {

    companion object {
        private val itemCallback = object : DiffUtil.ItemCallback<TVshow>() {
            override fun areItemsTheSame(oldItem: TVshow, newItem: TVshow): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }

            override fun areContentsTheSame(oldItem: TVshow, newItem: TVshow): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<out ViewDataBinding, TVshow> {
        return TVHolder(
            ItemTvshowBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            ItemClick
        )
    }

    inner class TVHolder(binding: ItemTvshowBinding, private val itemClickListener: (TVshow) -> Unit)
        : BaseViewHolder<ItemTvshowBinding, TVshow>(binding) {

        init {
            binding.apply {
                itemView.setOnClickListener {
                    tvshow?.run {
                        itemClickListener(this)
                    }
                }
            }
        }

        override fun bind(element: TVshow) {
            super.bind(element)
            binding.tvshow = element

        }
    }


}