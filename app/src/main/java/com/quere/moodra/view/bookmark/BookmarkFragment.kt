package com.quere.moodra.view.bookmark


import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.quere.moodra.R
import com.quere.moodra.adapter.BookmarkAdapter
import com.quere.moodra.databinding.FragmentBookmarkBinding
import com.quere.moodra.room.Bookmark
import com.quere.moodra.viewmodel.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_bookmark.*


@AndroidEntryPoint
class BookmarkFragment : Fragment() {

    private lateinit var binding: FragmentBookmarkBinding
    private val bookmarkviewModel by viewModels<DetailViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate<FragmentBookmarkBinding>(
            inflater,
            R.layout.fragment_bookmark,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {

            bookmarkEdit.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View?) {

                    if (bookmarkEdit.isSelected == false) {
                        ActivivateEditButton()
                    } else {
                        DeactivivateEditButton()


                    }

                }

            })

            bookmarkSubmit.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View?) {
                    DeactivivateButton(delete_button)

                    if (bookmarkSubmit.isSelected == false) {

                        ActivivateSubmitButton()
                    } else {
                        DeaactivivateSubmitButton()

                    }


                }

            })

            getBookAdapter(bookmarkRecyclerview, false)

            bookmarkviewModel.isSeleted.observe(viewLifecycleOwner, Observer { isSelected ->

                getBookAdapter(binding.bookmarkRecyclerview, isSelected)
            })



            bookmarkDeleteAll.setOnClickListener(object : View.OnClickListener {
                override fun onClick(p0: View?) {
                    DeleteAllDialog()
                }

            })


        }

    }

    override fun onPause() {
        super.onPause()
        bookmarkviewModel.setSeleted(false)
    }


    private fun moveBookmarkToDetail(bookmark: Bookmark, isSelected: Boolean) {


        if (isSelected == false) {
            val bookmarkToDetail =
                BookmarkFragmentDirections.actionNavBookmarkBookmarkToNavBookmarkDetail(
                    "movie",
                    bookmark.title!!,
                    bookmark.id!!,
                    bookmark.overview!!,
                    false,
                    bookmark.poster_path!!,
                    bookmark.backdrop_path!!,
                    bookmark.relese_date!!,
                    bookmark.vote_average!!

                )
            findNavController().navigate(bookmarkToDetail)
        }
    }

    private fun getBookAdapter(recyclerView: RecyclerView, isTrue: Boolean) {

        var adapter = BookmarkAdapter({ bookmark ->
            moveBookmarkToDetail(
                bookmark,
                binding.bookmarkEdit.isSelected
            )
        }, isTrue)

        bookmarkviewModel.bookmarks.observe(viewLifecycleOwner)
        { data ->
            adapter.submitList(data)
            if (data.size == 0) {
                bookmark_recyclerview.visibility = View.INVISIBLE
                noBookmark.visibility = View.VISIBLE

            }

            if (data.size > 0) {
                bookmark_recyclerview.visibility = View.VISIBLE
                noBookmark.visibility = View.INVISIBLE
            }
        }

        getRecyclerView(recyclerView, adapter)
    }


    fun getRecyclerView(recyclerView: RecyclerView, adapter: BookmarkAdapter) {

        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
        adapter.onItemSelectionChangeListener = { BookmarkSelectedList ->

            if (BookmarkSelectedList.size == 0) {

                delete_button.isEnabled = false
                DeactivivateButton(delete_button)

            }

            if (BookmarkSelectedList.size > 0) {

                delete_button.isEnabled = true
                ActivivateButton(delete_button)
                delete_button.setOnClickListener(object : View.OnClickListener {
                    override fun onClick(p0: View?) {

                        DeleteSelectionDialog(BookmarkSelectedList)


                    }
                })
            }
        }
    }


    fun DeleteAllDialog() {
        val DeleteAllDlg: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        DeleteAllDlg.setTitle("")
        DeleteAllDlg.setMessage("전체 삭제하시겠습니까?")
        DeleteAllDlg.setPositiveButton("삭제") { dialogInterface: DialogInterface, i: Int ->
            bookmarkviewModel.deleteAll()
        }
        DeleteAllDlg.setNegativeButton("아니요") { dialogInterface: DialogInterface, i: Int ->
            dialogInterface.cancel()
        }
        DeleteAllDlg.show()
    }


    fun DeleteSelectionDialog(selectedList: MutableList<Bookmark>) {


        val DeleteSelectionDlg: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        DeleteSelectionDlg.setTitle("")
        DeleteSelectionDlg.setMessage("선택 항목을 삭제하시겠습니까?")
        DeleteSelectionDlg.setPositiveButton("삭제") { dialogInterface: DialogInterface, i: Int ->
            for (i in 0..selectedList.size - 1) {
                DeactivivateButton(delete_button)
                bookmarkviewModel.delete(selectedList.get(i))
            }
            selectedList.clear()

        }
        DeleteSelectionDlg.setNegativeButton("아니요") { dialogInterface: DialogInterface, i: Int ->
            ActivivateButton(delete_button)
            dialogInterface.cancel()
        }
        DeleteSelectionDlg.show()
    }


    fun DeactivivateButton(delete_button: Button) {
        delete_button.setBackgroundColor(requireContext().resources.getColor(R.color.gray3))
    }

    fun ActivivateButton(delete_button: Button) {
        delete_button.setBackgroundColor(requireContext().resources.getColor(R.color.blue1))
    }

    fun ActivivateEditButton() {
        binding.apply {
            bookmarkEdit.isSelected = true
            bookmarkDeleteAll.visibility = View.GONE
            bookmarkEdit.visibility = View.GONE
            bookmarkSubmit.visibility = View.VISIBLE
            deleteButton.visibility = View.VISIBLE
            bookmarkviewModel.setSeleted(true)
        }

    }

    fun DeactivivateEditButton() {
        binding.apply {
            bookmarkEdit.isSelected = false
            bookmarkDeleteAll.visibility = View.VISIBLE
            bookmarkEdit.visibility = View.VISIBLE
            bookmarkSubmit.visibility = View.GONE
            deleteButton.visibility = View.GONE
            bookmarkviewModel.setSeleted(false)
        }

    }

    fun ActivivateSubmitButton() {
        binding.apply {
            bookmarkEdit.isSelected = false
            bookmarkDeleteAll.visibility = View.VISIBLE
            bookmarkEdit.visibility = View.VISIBLE
            bookmarkSubmit.visibility = View.GONE
            deleteButton.visibility = View.GONE
            bookmarkviewModel.setSeleted(bookmarkSubmit.isSelected)
        }
    }

    fun DeaactivivateSubmitButton() {
        binding.apply {
            bookmarkEdit.isSelected = true
            bookmarkDeleteAll.visibility = View.GONE
            bookmarkEdit.visibility = View.GONE
            bookmarkSubmit.visibility = View.VISIBLE
            deleteButton.visibility = View.VISIBLE
            bookmarkviewModel.setSeleted(bookmarkSubmit.isSelected)
        }
    }


}