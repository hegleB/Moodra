package com.quere.presenation.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.quere.domain.model.movie.Movie
import com.quere.presenation.R
import com.quere.presenation.base.BasePagingAdapter
import com.quere.presenation.base.BaseViewHolder
import com.quere.presenation.databinding.ItemMovieSearchBinding

class MovieSearchPagingAdapter(private val ItemClick : (Movie) -> Unit) : BasePagingAdapter<Movie>(itemCallback) {

    companion object {
        private val itemCallback = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }
        }
    }
    override fun getItemViewType(position: Int): Int {
        if (position == itemCount){
            return R.layout.search_detail_load
        }else {
            return R.layout.item_search_all
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<out ViewDataBinding, Movie> {
        return MovieHolder(
            ItemMovieSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            ItemClick
        )
    }

    inner class MovieHolder(binding: ItemMovieSearchBinding, private val itemClickListener: (Movie) -> Unit)
        : BaseViewHolder<ItemMovieSearchBinding, Movie>(binding) {

        init {
            binding.apply {
                executePendingBindings()
                itemView.setOnClickListener {
                    movie?.run {
                        itemClickListener(this)
                    }
                }
            }
        }

        override fun bind(element: Movie) {
            super.bind(element)
            binding.executePendingBindings()
            binding.movie = element

        }
    }
}