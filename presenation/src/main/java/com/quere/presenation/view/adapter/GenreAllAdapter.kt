package com.quere.presenation.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.quere.domain.model.common.Detail
import com.quere.presenation.R
import com.quere.presenation.base.BasePagingAdapter
import com.quere.presenation.base.BaseViewHolder
import com.quere.presenation.databinding.ItemGenreAllBinding

class GenreAllAdapter(private val ItemClick : (Detail) -> Unit) : BasePagingAdapter<Detail>(itemCallback) {

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
        return MovieHolder(
            ItemGenreAllBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            ItemClick
        )
    }

    override fun getItemViewType(position: Int): Int {
        if (position == itemCount){
            return R.layout.search_detail_load
        }else {
            return R.layout.item_genre_all
        }
    }

    inner class MovieHolder(binding: ItemGenreAllBinding, private val itemClickListener: (Detail) -> Unit)
        : BaseViewHolder<ItemGenreAllBinding, Detail>(binding) {

        init {
            binding.apply {
                itemView.setOnClickListener {
                    genre?.run {
                        itemClickListener(this)
                    }
                }
            }
        }

        override fun bind(element: Detail) {
            super.bind(element)
            binding.genre = element

        }
    }


}