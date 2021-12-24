package com.quere.moodra.adapter

import android.view.LayoutInflater

import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.quere.moodra.databinding.SearchDetailLoadBinding
import com.quere.moodra.databinding.SearchLoadFooterBinding


class SearchDetailLoadStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<SearchDetailLoadStateAdapter.LoadStateViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val binding = SearchDetailLoadBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return LoadStateViewHolder(binding)
    }


    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {

        holder.bind(loadState)

    }

    inner class LoadStateViewHolder(private val binding: SearchDetailLoadBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(loadState: LoadState){
            binding.apply {

                searchDetailLoadRetry.isVisible = loadState is LoadState.Error
                searchLoad.isVisible = loadState is LoadState.Loading
            }
        }

    }

}