package com.quere.presenation.view.genre

import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.quere.domain.model.common.Detail
import com.quere.presenation.R
import com.quere.presenation.base.BaseFragment
import com.quere.presenation.databinding.FragmentGenreAllBinding
import com.quere.presenation.view.adapter.GenreAllAdapter
import com.quere.presenation.view.adapter.HorizontalItemDecorator
import com.quere.presenation.view.adapter.SearchDetailLoadStateAdapter
import com.quere.presenation.viewmodel.DetailViewModel
import com.quere.presenation.viewmodel.GenreViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class GenreAllFragment : BaseFragment<FragmentGenreAllBinding>(R.layout.fragment_genre_all) {

    private val detailViewModel: DetailViewModel by activityViewModels()
    private val genreViewModel: GenreViewModel by activityViewModels()
    val genreArgs by navArgs<GenreAllFragmentArgs>()

    override fun initView() {

        init()
        observeViewModel()
        initToolbar()
        getGenreAllRecyclerView(binding.recyclerviewGenreAll)

    }

    private fun init() {
        setHasOptionsMenu(true)

    }

    private fun initToolbar() {
        binding.apply {
            textViewGenreAllGenre.setText(genreArgs.genre)
            toolbarGenreAll.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_24)
            toolbarGenreAll.setNavigationOnClickListener(object : View.OnClickListener {
                override fun onClick(p0: View?) {
                    findNavController().popBackStack()
                }
            })
        }
    }

    private fun observeViewModel() {
        genreViewModel.setGenre(genreArgs.genre)
    }

    private fun getGenreAllRecyclerView(recyclerView: RecyclerView) {
        val genreAdapter: GenreAllAdapter by lazy {
            GenreAllAdapter(
                ItemClick = { showDetail(genreArgs.type, it) }
            )
        }
        val gridLayout = GridLayoutManager(requireContext(), 3)
        gridLayout.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                var viewType = genreAdapter.getItemViewType(position)
                return if (viewType == R.layout.item_genre_all) 1
                else 3
            }

        }

        recyclerView.layoutManager = gridLayout
        recyclerView.adapter = genreAdapter.withLoadStateFooter(
            footer = SearchDetailLoadStateAdapter { genreAdapter.retry() }
        )
        recyclerView.addItemDecoration(HorizontalItemDecorator(10))


        collectFlow(genreArgs.type, genreArgs.genreId, genreAdapter)
        genreAdapter.addLoadStateListener { loadState ->
            binding.apply {

                if (loadState.refresh is LoadState.Loading) {
                    genreAllGenreDetailStl.isVisible = true
                }

                if (loadState.refresh is LoadState.NotLoading) {
                    genreAllGenreDetailStl.isVisible = false
                }
            }
        }
    }

    private fun collectFlow(type: String, genre: String, genreAdapter: GenreAllAdapter) {

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            genreViewModel.getGenreAll(type, genre).collectLatest { genreList ->
                genreAdapter.submitData(genreList)
            }
        }


    }


    private fun showDetail(type: String, detail: Detail) {
        when (type) {
            "movie" -> {
                viewLifecycleOwner.lifecycleScope.launch {
                    val movie = detailViewModel.getMovieDetail(detail.id ?: 0)
                    val MovieGenreDetailToDetail =
                        GenreAllFragmentDirections.actionGenreAllFragmentToDetailFragment(
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
                    findNavController().navigate(MovieGenreDetailToDetail)
                }
            }

            "tv" -> {
                viewLifecycleOwner.lifecycleScope.launch {
                    val tv = detailViewModel.getTVDetail(detail.id ?: 0)
                    val MovieGenreDetailToDetail =
                        GenreAllFragmentDirections.actionGenreAllFragmentToDetailFragment(
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
                    findNavController().navigate(MovieGenreDetailToDetail)
                }
            }
        }
    }
}