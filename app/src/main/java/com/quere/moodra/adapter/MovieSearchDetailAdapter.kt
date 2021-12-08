package com.quere.moodra.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.quere.moodra.R
import com.quere.moodra.databinding.ItemMovieSearchDetailBinding
import com.quere.moodra.retrofit.MovieSearch


class MovieSearchDetailAdapter(val searchItemClick: (MovieSearch) -> Unit, val searchItemLongClick: (MovieSearch) -> Unit) :

    PagingDataAdapter<MovieSearch, MovieSearchDetailAdapter.ViewHolder>(SearchDiffUtil) {

    inner class ViewHolder(val binding: ItemMovieSearchDetailBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(search: MovieSearch){
            binding.movieSearchDetail = search
            binding.executePendingBindings()
            binding.root.setOnClickListener{
                searchItemClick(search)


            }

            binding.root.setOnLongClickListener{
                searchItemLongClick(search)
                true
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemMovieSearchDetailBinding>(layoutInflater,viewType, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       val search = getItem(position)
        holder.bind(search!!)

    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_movie_search_detail
    }


    companion object SearchDiffUtil: DiffUtil.ItemCallback<MovieSearch>() {

        override fun areItemsTheSame(oldItem: MovieSearch, newItem: MovieSearch): Boolean {
            return oldItem==newItem
        }

        override fun areContentsTheSame(oldItem: MovieSearch, newItem: MovieSearch): Boolean {
            return oldItem==newItem
        }

    }

}

