package com.example.moviesimpleapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesimpleapp.R
import com.example.moviesimpleapp.databinding.ItemVideoBinding
import com.example.moviesimpleapp.retrofit.Trailer

class TrailerAdapter(
    val trailerItemClick: (Trailer) -> Unit,
    val trailerItemLongClick: (Trailer) -> Unit
)

    : PagingDataAdapter<Trailer, TrailerAdapter.ViewHolder>(TrailerAdapter) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding =
            DataBindingUtil.inflate<ItemVideoBinding>(layoutInflater, viewType, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_video
    }



    inner class ViewHolder(private val binding: ItemVideoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(trailer: Trailer) {
            binding.videos = trailer
            binding.executePendingBindings() // 데이터가 수정되면 즉각 바인딩

            binding.root.setOnClickListener {
                trailerItemClick(trailer)
            }
            binding.root.setOnLongClickListener {
                trailerItemLongClick(trailer)
                true
            }

        }

    }

    companion object MovieDiffUtil : DiffUtil.ItemCallback<Trailer>() {
        override fun areItemsTheSame(oldItem: Trailer, newItem: Trailer): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Trailer, newItem: Trailer): Boolean {
            return oldItem == newItem
        }

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val trailer = getItem(position)
        System.out.println(trailer)
        holder.bind(trailer!!)
    }
}