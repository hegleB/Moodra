package com.quere.presenation.base


import androidx.databinding.ViewDataBinding
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil

abstract class BasePagingAdapter<E: Any>
    (itemCallback: DiffUtil.ItemCallback<E>)
    : PagingDataAdapter<E, BaseViewHolder<out ViewDataBinding, E>>(itemCallback) {

    override fun onBindViewHolder(holder: BaseViewHolder<out ViewDataBinding, E>, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

}