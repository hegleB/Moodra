package com.example.moviesimpleapp.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesimpleapp.R
import com.example.moviesimpleapp.databinding.ItemMovieSearchBinding
import com.example.moviesimpleapp.databinding.ItemTvSearchBinding
import com.example.moviesimpleapp.retrofit.MovieSearch
import com.example.moviesimpleapp.retrofit.TVshowSearch


class TvSearchDataAdapter(val tvItemClick: (TVshowSearch) -> Unit, val tvItemLongClick: (TVshowSearch) -> Unit) :
    PagingDataAdapter<TVshowSearch, TvSearchDataAdapter.ViewHolder>(TvSearchDiffUtil) {


    inner class ViewHolder(private val binding: ItemTvSearchBinding) :RecyclerView.ViewHolder(binding.root) {
        fun bind(tvs: TVshowSearch){
            binding.tVsearch = tvs
            binding.executePendingBindings()

            binding.root.setOnClickListener {
                tvItemClick(tvs)
            }
            binding.root.setOnLongClickListener {
                tvItemLongClick(tvs)
                true
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemTvSearchBinding>(layoutInflater, viewType,parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie_credit = getItem(position)
        holder.bind(movie_credit!!)
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_tv_search
    }

    companion object TvSearchDiffUtil: DiffUtil.ItemCallback<TVshowSearch>() {

        override fun areItemsTheSame(oldItem: TVshowSearch, newItem: TVshowSearch): Boolean {
            return oldItem==newItem
        }

        override fun areContentsTheSame(oldItem: TVshowSearch, newItem: TVshowSearch): Boolean {
            return oldItem==newItem
        }

    }
}