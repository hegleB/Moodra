package com.quere.presenation.view.bookmark


import android.app.AlertDialog
import android.content.DialogInterface
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.quere.domain.model.common.Bookmark
import com.quere.domain.model.common.Detail
import com.quere.domain.model.movie.Movie
import com.quere.presenation.R
import com.quere.presenation.databinding.FragmentBookmarkBinding
import com.quere.presenation.base.BaseFragment
import com.quere.presenation.view.adapter.BookmarkAdapter
import com.quere.presenation.view.home.HomeFragmentDirections
import com.quere.presenation.viewmodel.BookmarkViewModel
import com.quere.presenation.viewmodel.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BookmarkFragment : BaseFragment<FragmentBookmarkBinding>(R.layout.fragment_bookmark) {

    private val bookmarkViewModel: BookmarkViewModel by activityViewModels()
    private val detailViewModel: DetailViewModel by activityViewModels()
    private lateinit var adapter: BookmarkAdapter

    override fun initView() {
        binding.viewmodel = bookmarkViewModel

        observeViewModel()
        getSavedBookmark()
        initAdapter()
    }


    private fun observeViewModel() {

        bookmarkViewModel.bookmarkEdit.observe(viewLifecycleOwner) {
            if (it.consumed) return@observe
            showEdit()
            it.consume()
        }

        bookmarkViewModel.bookmarkDeleteAll.observe(viewLifecycleOwner) {
            if (it.consumed) return@observe
            showDeleteAllDialog()
            it.consume()
        }

        bookmarkViewModel.bookmarkSubmit.observe(viewLifecycleOwner) {
            if (it.consumed) return@observe
            showEditComplete()
            it.consume()
        }

        bookmarkViewModel.bookmarkDeleteSelected.observe(viewLifecycleOwner) {
            if (it.consumed) return@observe
            showDeleteSelectionDialog()
            it.consume()
        }


        bookmarkViewModel.selectBookmark.observe(viewLifecycleOwner) {
            binding.apply {
                Log.d("BookMark", "${it.size}")
                if (it.size <= 0) {
                    buttonBookmarkDelete.isEnabled = false
                    buttonBookmarkDelete.setBackgroundColor(requireContext().resources.getColor(R.color.gray3))
                } else {
                    buttonBookmarkDelete.isEnabled = true
                    buttonBookmarkDelete.setBackgroundColor(requireContext().resources.getColor(R.color.blue1))
                }
            }
        }


    }

    private fun initAdapter() {

        bookmarkViewModel.isSelected.observe(viewLifecycleOwner) {

            adapter = BookmarkAdapter({
                if (it.type == "movie") {
                    showMovieDetail(it)
                } else {
                    showTVDetail(it)
                }
            }, it)
            binding.recyclerViewBookmarkList.adapter = adapter
            bookmarkViewModel.savedBookmarkList.observe(viewLifecycleOwner) {
                adapter.submitList(it)
            }

            adapter.onItemSelectionChangeListener = {
                bookmarkViewModel.selectBookmarkList(it)
            }
        }
    }


    private fun getSavedBookmark() = viewLifecycleOwner.lifecycleScope.launch {
        bookmarkViewModel.getSavedBookmark()

    }

    private fun showEdit() {
        ActivivateButton()
        binding.textViewBookmarkEdit.isSelected = true
    }

    private fun showEditComplete() {
        DeactivivateButton()
        binding.textViewBookmarkSubmit.isSelected = false
    }

    fun ActivivateButton() {
        binding.apply {
            textViewBookmarkDeleteAll.isVisible = false
            textViewBookmarkEdit.isVisible = false
            textViewBookmarkSubmit.isVisible = true
            buttonBookmarkDelete.isVisible = true
            bookmarkViewModel.setSelected(true)
        }

    }

    fun DeactivivateButton() {
        binding.apply {
            textViewBookmarkDeleteAll.visibility = View.VISIBLE
            textViewBookmarkEdit.visibility = View.VISIBLE
            textViewBookmarkSubmit.visibility = View.GONE
            buttonBookmarkDelete.visibility = View.GONE
            bookmarkViewModel.setSelected(false)
        }

    }

    private fun showDeleteAllDialog() {
        val DeleteAllDlg: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        DeleteAllDlg.setTitle("")
        DeleteAllDlg.setMessage("전체 삭제하시겠습니까?")
        DeleteAllDlg.setPositiveButton("삭제") { dialogInterface: DialogInterface, i: Int ->
            bookmarkViewModel.deleteAll()

        }
        DeleteAllDlg.setNegativeButton("아니요") { dialogInterface: DialogInterface, i: Int ->
            dialogInterface.cancel()
        }
        DeleteAllDlg.show()
    }

    fun showDeleteSelectionDialog() {
        var selectBookmarkList = mutableListOf<Bookmark>()
        bookmarkViewModel.selectBookmark.observe(viewLifecycleOwner) {
            selectBookmarkList = it
        }

        val DeleteSelectionDlg: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        DeleteSelectionDlg.setTitle("")
        DeleteSelectionDlg.setMessage("선택 항목을 삭제하시겠습니까?")
        DeleteSelectionDlg.setPositiveButton("삭제") { dialogInterface: DialogInterface, i: Int ->

            for (i in 0..selectBookmarkList.size - 1) {
                bookmarkViewModel.deleteSelectedBookmark(selectBookmarkList.get(i))
            }
            binding.buttonBookmarkDelete.isEnabled = false
            adapter.selectionList = arrayListOf()
            bookmarkViewModel.selectBookmarkList(mutableListOf())
        }

        DeleteSelectionDlg.setNegativeButton("아니요") { dialogInterface: DialogInterface, i: Int ->
            ActivivateButton()
            binding.buttonBookmarkDelete.isSelected = true
            dialogInterface.cancel()
        }
        DeleteSelectionDlg.show()
    }

    override fun onStop() {
        super.onStop()
        bookmarkViewModel.setSelected(false)
    }

    private fun showMovieDetail(item: Bookmark) {
        viewLifecycleOwner.lifecycleScope.launch {
            var movie = detailViewModel.getMovieDetail(item.id ?: 0)
            val bookmarkToDetail = BookmarkFragmentDirections.actionBookmarkNavFragmentToDetailFragment(
                Detail(
                    "movie",
                    movie.title,
                    "",
                    movie.genres,
                    movie.id,
                    movie.overview,
                    false,
                    movie.poster_path,
                    movie.backdrop_path,
                    movie.release_date,
                    movie.runtime,
                    movie.video,
                    movie.vote_average
                )
            )
            findNavController().navigate(bookmarkToDetail)
        }
    }

    private fun showTVDetail(item: Bookmark) {
        viewLifecycleOwner.lifecycleScope.launch {
            var tv = detailViewModel.getTVDetail(item.id ?: 0)
            val bookmarkToDetail = BookmarkFragmentDirections.actionBookmarkNavFragmentToDetailFragment(
                Detail(
                    "tv",
                    "",
                    tv.name,
                    tv.genres,
                    tv.id,
                    tv.overview,
                    false,
                    tv.poster_path,
                    tv.backdrop_path,
                    tv.first_air_date,
                    tv.episode_run_time.sum(),
                    tv.video,
                    tv.vote_average
                )
            )
            findNavController().navigate(bookmarkToDetail)
        }
    }
}