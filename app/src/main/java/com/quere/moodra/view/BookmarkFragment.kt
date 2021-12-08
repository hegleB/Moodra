package com.quere.moodra.view


import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_bookmark.*
import kotlinx.android.synthetic.main.fragment_search.*


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
        val arguments = arguments


        binding.apply {


            bookmarkEdit.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View?) {
                    if (bookmarkEdit.isSelected == false) {
                        bookmarkEdit.isSelected = true
                        bookmarkDeleteAll.visibility = View.GONE
                        bookmarkEdit.visibility = View.GONE
                        bookmarkSubmit.visibility = View.VISIBLE
                        deleteButton.visibility = View.VISIBLE
                        bookmarkviewModel.setSeleted(true)


                    } else {
                        bookmarkEdit.isSelected = false
                        bookmarkDeleteAll.visibility = View.VISIBLE
                        bookmarkEdit.visibility = View.VISIBLE
                        bookmarkSubmit.visibility = View.GONE
                        deleteButton.visibility = View.GONE
                        bookmarkviewModel.setSeleted(false)

                    }

                }

            })

            bookmarkSubmit.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View?) {
                    deleteButton.setBackgroundColor(Color.parseColor("#9E9E9E"))
                    if (bookmarkSubmit.isSelected == false) {
                        bookmarkEdit.isSelected = false
                        bookmarkDeleteAll.visibility = View.VISIBLE
                        bookmarkEdit.visibility = View.VISIBLE
                        bookmarkSubmit.visibility = View.GONE
                        deleteButton.visibility = View.GONE
                        bookmarkviewModel.setSeleted(bookmarkSubmit.isSelected)


                    } else {
                        bookmarkEdit.isSelected = true
                        bookmarkDeleteAll.visibility = View.GONE
                        bookmarkEdit.visibility = View.GONE
                        bookmarkSubmit.visibility = View.VISIBLE
                        deleteButton.visibility = View.VISIBLE
                        bookmarkviewModel.setSeleted(bookmarkSubmit.isSelected)

                    }


                }

            })
            bookAdapter(binding.bookmarkRecyclerview, false)
            bookmarkviewModel.isSeleted.observe(viewLifecycleOwner, Observer {
                bookAdapter(binding.bookmarkRecyclerview, it)
            })



            bookmarkDeleteAll.setOnClickListener(object : View.OnClickListener {

                override fun onClick(p0: View?) {

                    val dlg: AlertDialog.Builder = AlertDialog.Builder(requireContext())
                    dlg.setTitle("")
                    dlg.setMessage("전체 삭제하시겠습니까?")
                    dlg.setPositiveButton("삭제") { dialogInterface: DialogInterface, i: Int ->
                        bookmarkviewModel.deleteAll()
                    }
                    dlg.setNegativeButton("아니요") { dialogInterface: DialogInterface, i: Int ->
                        dialogInterface.cancel()
                    }
                    dlg.show()


                }

            })


        }

    }

    override fun onPause() {
        super.onPause()
        bookmarkviewModel.setSeleted(false)
    }


    private fun bookmarkDialog(bookmark: Bookmark, isSelected: Boolean) {


            if (isSelected == false) {
                val direction = BookmarkFragmentDirections.actionBookmarkFragmentToDetailFragment2(
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
                findNavController().navigate(direction)
            }
    }

    fun bookAdapter(recyclerView: RecyclerView, isTrue: Boolean) {

        var adapter = BookmarkAdapter({ bookmark ->
            bookmarkDialog(
                bookmark,
                binding.bookmarkEdit.isSelected
            )
        }, isTrue)

        bookmarkviewModel.bookmarks.observe(viewLifecycleOwner)
        { data ->
            adapter.submitList(data)
            if(data.size==0){
                bookmark_recyclerview.visibility=View.INVISIBLE
                noBookmark.visibility=View.VISIBLE

            } else {
                bookmark_recyclerview.visibility=View.VISIBLE
                noBookmark.visibility=View.INVISIBLE
            }
        }
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
        adapter.onItemSelectionChangeListener = {

            if(it.size==0){

                delete_button.isEnabled=false
                delete_button.setBackgroundColor(Color.parseColor("#9E9E9E"))

            } else {

                delete_button.isEnabled = true
                delete_button.setBackgroundColor(Color.parseColor("#1565C0"))
                delete_button.setOnClickListener(object : View.OnClickListener{
                    override fun onClick(p0: View?) {
                        delete_button.setBackgroundColor(Color.parseColor("#9E9E9E"))
                        val dlg: AlertDialog.Builder = AlertDialog.Builder(requireContext())
                        dlg.setTitle("")
                        dlg.setMessage("선택 항목을 삭제하시겠습니까?")
                        dlg.setPositiveButton("삭제") { dialogInterface: DialogInterface, i: Int ->
                            for (i in 0..it.size - 1) {
                                bookmarkviewModel.delete(it.get(i))

                            }
                        }
                        dlg.setNegativeButton("아니요") { dialogInterface: DialogInterface, i: Int ->
                            dialogInterface.cancel()
                        }
                        dlg.show()

                    }
                })
            }



        }
    }


}