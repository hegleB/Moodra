package com.quere.presenation.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.quere.domain.model.common.OtherContent
import com.quere.presenation.base.BasePagingAdapter
import com.quere.presenation.base.BaseViewHolder
import com.quere.presenation.databinding.ItemContentsBinding

class ContentsAdapter(private val ItemClick : (OtherContent) -> Unit) : BasePagingAdapter<OtherContent>(itemCallback) {

    companion object {
        private val itemCallback = object : DiffUtil.ItemCallback<OtherContent>() {
            override fun areItemsTheSame(oldItem: OtherContent, newItem: OtherContent): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }

            override fun areContentsTheSame(oldItem: OtherContent, newItem: OtherContent): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<out ViewDataBinding, OtherContent> {
        return ContentHolder(
            ItemContentsBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            ItemClick
        )
    }

    inner class ContentHolder(binding: ItemContentsBinding, private val itemClickListener: (OtherContent) -> Unit)
        : BaseViewHolder<ItemContentsBinding, OtherContent>(binding) {

        init {
            binding.apply {
                itemView.setOnClickListener {
                    contents?.run {
                        itemClickListener(this)
                    }
                }
            }
        }

        override fun bind(element: OtherContent) {
            super.bind(element)
            binding.contents = element

        }
    }


}