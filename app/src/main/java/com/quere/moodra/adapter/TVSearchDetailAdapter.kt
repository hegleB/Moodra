package com.quere.moodra.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.quere.moodra.R
import com.quere.moodra.databinding.ItemTvSearchDetailBinding
import com.quere.moodra.retrofit.TVshowSearch

class TVSearchDetailAdapter(val searchItemClick: (TVshowSearch) -> Unit, val searchItemLongClick: (TVshowSearch) -> Unit) :

    PagingDataAdapter<TVshowSearch, TVSearchDetailAdapter.ViewHolder>(SearchDiffUtil) {

    inner class ViewHolder(val binding: ItemTvSearchDetailBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(search: TVshowSearch){
            binding.tvSearchDetail = search
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
        val binding = DataBindingUtil.inflate<ItemTvSearchDetailBinding>(layoutInflater,viewType, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val search = getItem(position)

        holder.bind(search!!)
    }

    override fun getItemViewType(position: Int): Int {
        if (position == itemCount){
            return R.layout.search_detail_load
        }else {
            return R.layout.item_tv_search_detail
        }
    }

    companion object SearchDiffUtil: DiffUtil.ItemCallback<TVshowSearch>() {

        override fun areItemsTheSame(oldItem: TVshowSearch, newItem: TVshowSearch): Boolean {
            return oldItem==newItem
        }

        override fun areContentsTheSame(oldItem: TVshowSearch, newItem: TVshowSearch): Boolean {
            return oldItem==newItem
        }

    }

}