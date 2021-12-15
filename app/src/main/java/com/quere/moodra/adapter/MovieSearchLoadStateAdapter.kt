package com.quere.moodra.adapter

import android.view.LayoutInflater

import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.quere.moodra.databinding.SearchLoadFooterBinding


class MovieSearchLoadStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<MovieSearchLoadStateAdapter.LoadStateViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val binding = SearchLoadFooterBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return LoadStateViewHolder(binding)
    }


    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    inner class LoadStateViewHolder(private val binding: SearchLoadFooterBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(loadState: LoadState){
            binding.apply {

                errorMsg.isVisible = loadState is LoadState.Error
            }
        }

    }

}