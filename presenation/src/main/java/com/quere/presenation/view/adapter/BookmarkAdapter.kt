package com.quere.presenation.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.quere.domain.model.common.Bookmark
import com.quere.domain.model.common.Detail
import com.quere.presenation.databinding.ItemBookmarkBinding


class BookmarkAdapter(private val ItemClick : (Bookmark) -> Unit,val isSelected: Boolean) :
    ListAdapter<Bookmark, BookmarkAdapter.ViewHolder>(
        DiffCallBack
    ) {
    var selectionList: ArrayList<Bookmark> = arrayListOf()
    var onItemSelectionChangeListener: ((MutableList<Bookmark>) -> Unit)? = null

    inner class ViewHolder(val binding: ItemBookmarkBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(bookmark: Bookmark) {
            binding.apply {

                binding.bookmark = bookmark

                if (isSelected) {
                    imageViewItemBookmarkEmptyCheckbox.visibility = View.VISIBLE
                } else {
                    imageViewItemBookmarkEmptyCheckbox.visibility = View.GONE
                    selectionList.clear()
                }

                if (selectionList.isEmpty()) {
                    imageViewItemBookmarkCheckbox.visibility = View.GONE
                }

                root.setOnClickListener(object : View.OnClickListener {
                    override fun onClick(v: View?) {

                        if (isSelected == true) {
                            if (root.isSelected == false) {
                                root.isSelected = true
                                imageViewItemBookmarkCheckbox.visibility = View.VISIBLE
                                selectionList.add(bookmark)

                            } else {
                                root.isSelected = false
                                imageViewItemBookmarkCheckbox.visibility = View.GONE
                                selectionList.remove(bookmark)

                            }
                        } else {
                            ItemClick(bookmark)
                        }
                        onItemSelectionChangeListener?.let { it(selectionList) }
                    }

                })
            }
        }

    }

    companion object DiffCallBack : DiffUtil.ItemCallback<Bookmark>() {
        override fun areContentsTheSame(oldItem: Bookmark, newItem: Bookmark) =
            oldItem.title == newItem.title

        override fun areItemsTheSame(oldItem: Bookmark, newItem: Bookmark) = oldItem == newItem

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            ItemBookmarkBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        holder.bind(item)
    }
}