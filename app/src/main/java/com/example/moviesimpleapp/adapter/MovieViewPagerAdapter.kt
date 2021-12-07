package com.example.moviesimpleapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil

import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesimpleapp.R
import com.example.moviesimpleapp.databinding.ItemMovieViewpagerBinding
import com.example.moviesimpleapp.retrofit.Movie


class MovieViewPagerAdapter(
    val movieItemClick: (Movie) -> Unit,
    val movieItemLongClick: (Movie) -> Unit
) : PagingDataAdapter<Movie, MovieViewPagerAdapter.ViewHolder>(MovieDiffUtil) {
    companion object MovieDiffUtil : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemMovieViewpagerBinding>(
            layoutInflater,
            viewType,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_movie_viewpager
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(movie!!)

    }


    inner class ViewHolder(private val binding: ItemMovieViewpagerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            binding.viewpager = movie

            binding.root.setOnClickListener {
                movieItemClick(movie)

            }
            binding.root.setOnLongClickListener {
                movieItemLongClick(movie)
                true
            }

        }

    }
}
