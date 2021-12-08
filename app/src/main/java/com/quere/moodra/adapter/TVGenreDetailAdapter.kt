package com.quere.moodra.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil

import androidx.recyclerview.widget.RecyclerView
import com.quere.moodra.R
import com.quere.moodra.databinding.ItemTvGenreDetailBinding


import com.quere.moodra.retrofit.TVshow


class TVGenreDetailAdapter(
    val tvItemClick: (TVshow) -> Unit,
    val tvItemLongClick: (TVshow) -> Unit
)

    : PagingDataAdapter<TVshow, TVGenreDetailAdapter.ViewHolder>(TvDiffUtil) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding =
            DataBindingUtil.inflate<ItemTvGenreDetailBinding>(layoutInflater, viewType, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_tv_genre_detail
    }



    inner class ViewHolder(private val binding: ItemTvGenreDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(tv: TVshow) {
            binding.tvGenreDetail = tv
            binding.executePendingBindings() // 데이터가 수정되면 즉각 바인딩

            binding.root.setOnClickListener {
                tvItemClick(tv)
            }
            binding.root.setOnLongClickListener {
                tvItemLongClick(tv)
                true
            }

        }

    }

    companion object TvDiffUtil : DiffUtil.ItemCallback<TVshow>() {
        override fun areItemsTheSame(oldItem: TVshow, newItem: TVshow): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: TVshow, newItem: TVshow): Boolean {
            return oldItem == newItem
        }

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tv = getItem(position)

        holder.bind(tv!!)
    }
}

