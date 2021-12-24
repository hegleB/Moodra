package com.quere.moodra.adapter

import com.quere.moodra.retrofit.Movie



import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.quere.moodra.R
import com.quere.moodra.databinding.ItemMovieBinding


class MovieAdapter(val movieItemClick: (Movie) -> Unit, val movieItemLongClick: (Movie) -> Unit)
    : PagingDataAdapter<Movie, MovieAdapter.ViewHolder>(MovieDiffUtil){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemMovieBinding>(layoutInflater, viewType,parent,false)

        return ViewHolder(binding)
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_movie
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        val movie = getItem(position)
        holder.bind(movie!!)
    }


    inner class ViewHolder(private val binding:ItemMovieBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie){
            binding.movie = movie
            binding.executePendingBindings()

            binding.root.setOnClickListener {
                movieItemClick(movie)
            }
            binding.root.setOnLongClickListener {
                movieItemLongClick(movie)
                true
            }

        }

    }

    companion object MovieDiffUtil: DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem==newItem
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem==newItem
        }

    }
}