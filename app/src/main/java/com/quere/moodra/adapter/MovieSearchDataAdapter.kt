package com.quere.moodra.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.quere.moodra.R
import com.quere.moodra.databinding.ItemMovieSearchBinding
import com.quere.moodra.retrofit.MovieSearch



class MovieSearchDataAdapter(val movieItemClick: (MovieSearch) -> Unit, val movieItemLongClick: (MovieSearch) -> Unit) :
    PagingDataAdapter<MovieSearch, MovieSearchDataAdapter.ViewHolder>(MovieSearchDiffUtil) {


    inner class ViewHolder(private val binding: ItemMovieSearchBinding) :RecyclerView.ViewHolder(binding.root) {
        fun bind(movies: MovieSearch){
            binding.moviesearch = movies

            binding.executePendingBindings()

            binding.root.setOnClickListener {
                movieItemClick(movies)
            }
            binding.root.setOnLongClickListener {
                movieItemLongClick(movies)
                true
            }

        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemMovieSearchBinding>(layoutInflater, viewType,parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie_credit = getItem(position)
        holder.bind(movie_credit!!)

    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_movie_search
    }

    companion object MovieSearchDiffUtil: DiffUtil.ItemCallback<MovieSearch>() {

        override fun areItemsTheSame(oldItem: MovieSearch, newItem: MovieSearch): Boolean {
            return oldItem==newItem
        }

        override fun areContentsTheSame(oldItem: MovieSearch, newItem: MovieSearch): Boolean {
            return oldItem==newItem
        }

    }
}