package com.example.moviesimpleapp.adapter

import com.example.moviesimpleapp.databinding.ItemGenreMovieBinding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil

import androidx.recyclerview.widget.RecyclerView
import com.example.moviesimpleapp.R

import com.example.moviesimpleapp.retrofit.Movie


class MovieGenreAdatper(
    val movieItemClick: (Movie) -> Unit,
    val movieItemLongClick: (Movie) -> Unit
)

    : PagingDataAdapter<Movie, MovieGenreAdatper.ViewHolder>(MovieAdapter) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding =
            DataBindingUtil.inflate<ItemGenreMovieBinding>(layoutInflater, viewType, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_genre_movie
    }



    inner class ViewHolder(private val binding: ItemGenreMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            binding.genreMovie = movie
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

    companion object MovieDiffUtil : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = getItem(position)

        holder.bind(movie!!)
    }
}

