package com.quere.presenation.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.quere.domain.model.common.Actor
import com.quere.presenation.base.BaseAdapter
import com.quere.presenation.base.BaseViewHolder
import com.quere.presenation.databinding.ItemCreditBinding

class CreditAdapter : BaseAdapter<Actor>(itemCallback) {

    companion object {
        private val itemCallback = object : DiffUtil.ItemCallback<Actor>() {
            override fun areItemsTheSame(oldItem: Actor, newItem: Actor): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }

            override fun areContentsTheSame(oldItem: Actor, newItem: Actor): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<out ViewDataBinding, Actor> {
        return CreditHolder(
            ItemCreditBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    inner class CreditHolder(binding: ItemCreditBinding)
        : BaseViewHolder<ItemCreditBinding, Actor>(binding) {

        override fun bind(element: Actor) {
            super.bind(element)
            binding.credit = element

        }
    }


}