package com.quere.presenation.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.quere.domain.model.movie.Movie
import com.quere.presenation.base.BasePagingAdapter
import com.quere.presenation.base.BaseViewHolder
import com.quere.presenation.databinding.ItemMovieBinding

class MoviePagingAdapter(private val ItemClick : (Movie) -> Unit) : BasePagingAdapter<Movie>(itemCallback) {

    companion object {
        private val itemCallback = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<out ViewDataBinding, Movie> {
        return MovieHolder(
            ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            ItemClick
        )
    }

    inner class MovieHolder(binding: ItemMovieBinding, private val itemClickListener: (Movie) -> Unit)
        : BaseViewHolder<ItemMovieBinding, Movie>(binding) {

        init {
            binding.apply {
                itemView.setOnClickListener {
                    movie?.run {
                        itemClickListener(this)
                    }
                }
            }
        }

        override fun bind(element: Movie) {
            super.bind(element)
            binding.movie = element

        }
    }
}