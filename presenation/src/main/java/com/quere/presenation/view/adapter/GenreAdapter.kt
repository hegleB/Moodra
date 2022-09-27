package com.quere.presenation.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.quere.domain.model.common.Detail
import com.quere.presenation.base.BasePagingAdapter
import com.quere.presenation.base.BaseViewHolder
import com.quere.presenation.databinding.ItemGenreBinding

class GenreAdapter(private val ItemClick : (Detail) -> Unit) : BasePagingAdapter<Detail>(itemCallback) {

    companion object {
        private val itemCallback = object : DiffUtil.ItemCallback<Detail>() {
            override fun areItemsTheSame(oldItem: Detail, newItem: Detail): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }

            override fun areContentsTheSame(oldItem: Detail, newItem: Detail): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<out ViewDataBinding, Detail> {
        return CreditHolder(
            ItemGenreBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        ItemClick)

    }

    inner class CreditHolder(binding: ItemGenreBinding,private val ItemClick : (Detail) -> Unit)
        : BaseViewHolder<ItemGenreBinding, Detail>(binding) {

        init {
            binding.apply {
                itemView.setOnClickListener {
                    genre?.run {
                        ItemClick(this)
                    }
                }
            }
        }

        override fun bind(element: Detail) {
            super.bind(element)
            binding.genre= element

        }
    }


}