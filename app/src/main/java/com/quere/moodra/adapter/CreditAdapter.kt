package com.quere.moodra.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.quere.moodra.R
import com.quere.moodra.databinding.ItemCreditBinding
import com.quere.moodra.retrofit.Actor


class CreditAdapter() : ListAdapter<Actor, CreditAdapter.ViewHolder>(MovieCreditDiffUtil) {


    inner class ViewHolder(private val binding: ItemCreditBinding) :RecyclerView.ViewHolder(binding.root) {
        fun bind(actor: Actor){
            binding.credit = actor
            binding.executePendingBindings()

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemCreditBinding>(layoutInflater, viewType,parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie_credit = currentList[position]
        holder.bind(movie_credit)
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_credit
    }

    companion object MovieCreditDiffUtil: DiffUtil.ItemCallback<Actor>() {

        override fun areItemsTheSame(oldItem: Actor, newItem: Actor): Boolean {
            return oldItem==newItem
        }

        override fun areContentsTheSame(oldItem: Actor, newItem: Actor): Boolean {
            return oldItem==newItem
        }

    }
}