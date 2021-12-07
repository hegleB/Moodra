package com.example.tvsimpleapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesimpleapp.R
import com.example.moviesimpleapp.databinding.ItemTvViewpagerBinding
import com.example.moviesimpleapp.retrofit.TVshow




class TVshowViewPagerAdapter(val tvItemClick: (TVshow) -> Unit, val tvItemLongClick: (TVshow) -> Unit)
    : PagingDataAdapter<TVshow, TVshowViewPagerAdapter.ViewHolder>(tvDiffUtil){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemTvViewpagerBinding>(layoutInflater, viewType,parent,false)
        return ViewHolder(binding)
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_tv_viewpager
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val tv = getItem(position)
        holder.bind(tv!!)
    }

    inner class ViewHolder(private val binding: ItemTvViewpagerBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(tv: TVshow){
            binding.viewpager = tv
            binding.executePendingBindings() // 데이터가 수정되면 즉각 바인딩
            binding.root.setOnClickListener {
                tvItemClick(tv)
                System.out.println("아이템!!!!")
            }
            binding.root.setOnLongClickListener {
                tvItemLongClick(tv)
                true
            }

        }

    }

    companion object tvDiffUtil: DiffUtil.ItemCallback<TVshow>() {
        override fun areItemsTheSame(oldItem: TVshow, newItem: TVshow): Boolean {
            return oldItem==newItem
        }

        override fun areContentsTheSame(oldItem: TVshow, newItem: TVshow): Boolean {
            return oldItem==newItem
        }

    }
}