package com.quere.presenation.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.quere.domain.model.common.Trailer
import com.quere.presenation.base.BaseAdapter
import com.quere.presenation.base.BaseViewHolder
import com.quere.presenation.databinding.ItemVideoBinding

class TrailerAdapter(private val ItemClick : (Trailer) -> Unit) : BaseAdapter<Trailer>(itemCallback) {

    companion object {
        private val itemCallback = object : DiffUtil.ItemCallback<Trailer>() {
            override fun areItemsTheSame(oldItem: Trailer, newItem: Trailer): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }

            override fun areContentsTheSame(oldItem: Trailer, newItem: Trailer): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<out ViewDataBinding, Trailer> {
        return TrailerHolder(
            ItemVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            ItemClick
        )
    }

    inner class TrailerHolder(binding: ItemVideoBinding, private val itemClickListener: (Trailer) -> Unit)
        : BaseViewHolder<ItemVideoBinding, Trailer>(binding) {

        init {
            binding.apply {
                itemView.setOnClickListener {
                    videos?.run {
                        itemClickListener(this)
                    }
                }
            }
        }

        override fun bind(element: Trailer) {
            super.bind(element)
            binding.videos = element

        }
    }


}