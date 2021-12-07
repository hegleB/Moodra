package com.example.moviesimpleapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesimpleapp.R
import com.example.moviesimpleapp.databinding.ItemContentsBinding
import com.example.moviesimpleapp.retrofit.OtherContent

class DetailContentsAdapter(val movieItemClick: (OtherContent) -> Unit, val movieItemLongClick: (OtherContent) -> Unit)
    : PagingDataAdapter<OtherContent, DetailContentsAdapter.ViewHolder>(MovieDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding =
            DataBindingUtil.inflate<ItemContentsBinding>(layoutInflater, viewType, parent, false)

        return ViewHolder(binding)
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_contents
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        val movie = getItem(position)
        holder.bind(movie!!)
    }


    inner class ViewHolder(private val binding: ItemContentsBinding ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: OtherContent) {
            binding.contents = movie

            binding.executePendingBindings() // 데이터가 수정되면 즉각 바인딩

            binding.root.setOnClickListener {
                movieItemClick(movie)
            }
            binding.root.setOnLongClickListener {
                movieItemLongClick(movie)
                true
            }

        }

    }

    companion object MovieDiffUtil : DiffUtil.ItemCallback<OtherContent>() {
        override fun areItemsTheSame(oldItem: OtherContent, newItem: OtherContent): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: OtherContent, newItem: OtherContent): Boolean {
            return oldItem == newItem
        }

    }
}
