package com.quere.presenation.view.search


import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.quere.domain.model.common.Detail
import com.quere.domain.model.movie.Movie
import com.quere.domain.model.tv.TVshow
import com.quere.presenation.R
import com.quere.presenation.base.BaseFragment
import com.quere.presenation.databinding.FragmentSearchAllBinding
import com.quere.presenation.view.adapter.*
import com.quere.presenation.viewmodel.DetailViewModel
import com.quere.presenation.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchAllFragment : BaseFragment<FragmentSearchAllBinding>(R.layout.fragment_search_all) {

    private val searchViewModel: SearchViewModel by activityViewModels()
    private val detailViewModel: DetailViewModel by activityViewModels()
    private val args by navArgs<SearchAllFragmentArgs>()

    override fun initView() {
        initAdapter()
        initToolbar()
    }

    private fun initAdapter() {

        if (args.type == "movie") {
            movieSearchAll()
        } else {
            tvSearchAll()
        }

    }

    private fun initToolbar() {
        binding.apply {
            toolBarSearchAll.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_24)
            toolBarSearchAll.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
            textViewSearchAllQuery.setText(args.query)
        }
    }

    private fun movieSearchAll() {
        val adapter = MovieSearchPagingAdapter({
            showMovieDetail(it)
        })
        binding.apply {
            val gridLayout = GridLayoutManager(requireContext(), 3)
            gridLayout.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    var viewType = adapter.getItemViewType(position)
                    return if (viewType == R.layout.item_search_all) 1
                    else 3
                }

            }


            recyclierViewSearchAll.layoutManager = gridLayout
            recyclierViewSearchAll.addItemDecoration(HorizontalItemDecorator(10))
            recyclierViewSearchAll.setHasFixedSize(true)
            recyclierViewSearchAll.adapter = adapter.withLoadStateFooter(
                footer = SearchDetailLoadStateAdapter { adapter.retry() }
            )


            viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
                searchViewModel.getMovieSearchAll(args.query).collectLatest {
                    adapter.submitData(it)
                }
            }



            adapter.addLoadStateListener { loadState ->
                binding.apply {

                    if (loadState.refresh is LoadState.Loading) {
                        stlSearchAll.isVisible = true
                    }

                    if (loadState.refresh is LoadState.NotLoading) {
                        stlSearchAll.isVisible = false
                    }
                }
            }
        }


    }

    private fun tvSearchAll() {
        val adapter = TVSearchPagingAdapter({
            showTVDetail(it)
        })
        binding.apply {
            val gridLayout = GridLayoutManager(requireContext(), 3)

            gridLayout.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    var viewType = adapter.getItemViewType(position)
                    return if (viewType == R.layout.item_search_all) 1
                    else 3
                }

            }

            adapter.addLoadStateListener { loadState ->
                binding.apply {

                    if (loadState.refresh is LoadState.Loading) {
                        stlSearchAll.isVisible = true
                    } else if (loadState.refresh is LoadState.NotLoading) {
                        stlSearchAll.isVisible = false
                    }
                }
            }
            recyclierViewSearchAll.adapter = adapter
            recyclierViewSearchAll.layoutManager = gridLayout
        }

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            searchViewModel.getTVSearchAll(args.query).collectLatest {
                adapter.submitData(it)
            }
        }

    }

    private fun showMovieDetail(item: Movie) {
        viewLifecycleOwner.lifecycleScope.launch {
            val movie = detailViewModel.getMovieDetail(item.id)
            val MovieToDetail = SearchAllFragmentDirections.actionSearchAllFragmentToDetailFragment(
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
            findNavController().navigate(MovieToDetail)
        }

    }

    private fun showTVDetail(item: TVshow) {
        viewLifecycleOwner.lifecycleScope.launch {
            val tv = detailViewModel.getTVDetail(item.id)
            val TvToDetail = SearchAllFragmentDirections.actionSearchAllFragmentToDetailFragment(
                Detail(
                    "tv",
                    "",
                    tv.name!!,
                    tv.genres,
                    tv.id,
                    tv.overview!!,
                    false,
                    tv.poster_path!!,
                    tv.backdrop_path!!,
                    tv.first_air_date!!,
                    0,
                    tv.video,
                    tv.vote_average!!
                )
            )
            findNavController().navigate(TvToDetail)
        }

    }
}