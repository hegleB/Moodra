package com.quere.moodra.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.quere.moodra.AppConstants
import com.quere.moodra.databinding.ItemBookmarkBinding
import com.quere.moodra.room.Bookmark


class BookmarkAdapter(val bookmarkItemClick: (Bookmark) -> Unit, val isSeleted: Boolean) :
    ListAdapter<Bookmark, BookmarkAdapter.ViewHolder>(
        DiffCallBack
    ) {

    var selectionList : ArrayList<Bookmark> = arrayListOf()

    var onItemSelectionChangeListener: ((MutableList<Bookmark>) -> Unit)? = null

    inner class ViewHolder(val binding: ItemBookmarkBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(bookmark: Bookmark, position: Int) {


            binding.apply {
                Glide.with(itemView)
                    .load(AppConstants.base_imageUrl + bookmark.poster_path)
                    .into(bookmarkImage)

                bookmarkTitle.text = bookmark.title
                bookmarkOverview.text = bookmark.overview
                if (isSeleted == true) {
                    checkBoxBackground.visibility = View.VISIBLE
                } else {
                    checkBoxBackground.visibility = View.GONE
                    selectionList.clear()
                }
                root.setOnClickListener(object : View.OnClickListener {
                    override fun onClick(v: View?) {

                        if (isSeleted == true) {
                            if (root.isSelected == false) {
                                root.isSelected = true
                                checkBox.visibility = View.VISIBLE
                                bookmarkItemClick(bookmark)
                                selectionList.add(bookmark)

                            } else {

                                root.isSelected = false
                                checkBox.visibility = View.GONE
                                bookmarkItemClick(bookmark)
                                selectionList.remove(bookmark)

                            }
                        }
                        else{
                            bookmarkItemClick(bookmark)
                        }

                        onItemSelectionChangeListener?.let{ it(selectionList)}
                    }

                })
            }
        }

    }

    companion object DiffCallBack : DiffUtil.ItemCallback<Bookmark>() {
        override fun areContentsTheSame(oldItem: Bookmark, newItem: Bookmark) =
            oldItem.id == newItem.id

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
        holder.bind(item, position)
    }
}