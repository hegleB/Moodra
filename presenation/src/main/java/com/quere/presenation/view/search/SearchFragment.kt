package com.quere.presenation.view.search

import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.quere.domain.model.common.Detail
import com.quere.domain.model.movie.Movie
import com.quere.domain.model.tv.TVshow
import com.quere.presenation.R
import com.quere.presenation.databinding.FragmentSearchBinding
import com.quere.presenation.base.BaseFragment
import com.quere.presenation.view.adapter.MovieSearchPagingAdapter
import com.quere.presenation.view.adapter.TVSearchPagingAdapter
import com.quere.presenation.viewmodel.DetailViewModel
import com.quere.presenation.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(R.layout.fragment_search) {

    private val detailViewModel: DetailViewModel by activityViewModels()
    private val searchViewModel: SearchViewModel by activityViewModels()
    private val movieAdapter = MovieSearchPagingAdapter({
        showMovieDetail(it)
    })
    private val tvAdapter = TVSearchPagingAdapter({
        showTVDetail(it)
    })

    override fun initView() {
        binding.searchViewSearch.setIconifiedByDefault(false)
        initRecyclerView()
        collectFlow()
        observeViewModel()
        getSearchQuery()
        initViewModel()
    }

    override fun onStart() {
        super.onStart()
        searchViewModel.searchQuery.observe(viewLifecycleOwner) {
            if (it.length>0) {
                binding.frameLayoutSearchNoSearch.visibility = View.GONE
            } else {
                binding.frameLayoutSearchNoSearch.visibility = View.VISIBLE
            }
        }
    }


    private fun initViewModel() {
        binding.viewmodel = searchViewModel
    }

    private fun observeViewModel() {

        searchViewModel.movieSearchAll.observe(viewLifecycleOwner) {
            if (it.consumed) return@observe
            showMovieSearchAll()
            it.consume()
        }

        searchViewModel.tvSearchAll.observe(viewLifecycleOwner) {
            if (it.consumed) return@observe
            showTVSearchAll()
            it.consume()
        }

    }

    private fun getSearchQuery() {
        binding.searchViewSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchViewModel.setSearchQuery(query?:"")
                binding.searchViewSearch.clearFocus()
                binding.frameLayoutSearchNoSearch.visibility = View.GONE
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {

                if (query=="") {
                    searchViewModel.setSearchQuery("")
                    binding.frameLayoutSearchNoSearch.visibility = View.VISIBLE
                }
                return true
            }
        })
    }

    private fun initRecyclerView() {
        binding.apply {

            recyclerViewSearchMovie.adapter = movieAdapter
            recyclerViewSearchTv.adapter = tvAdapter

        }
    }

    private fun collectFlow() {

        searchViewModel.searchQuery.observe(viewLifecycleOwner) {
            viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
                searchViewModel.getMovieSearch(it).collectLatest {
                    movieAdapter.submitData(it)
                }
            }

            viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
                searchViewModel.getTVSearch(it).collectLatest {
                    tvAdapter.submitData(it)
                }
            }
        }


    }

    private fun showMovieSearchAll() {
        searchViewModel.searchQuery.observe(viewLifecycleOwner) {
            val toSearchAll = SearchFragmentDirections.actionSearchNavFragmentToSearchAllFragment(it,"movie")
            findNavController().navigate(toSearchAll)
        }
    }
    private fun showTVSearchAll() {
        searchViewModel.searchQuery.observe(viewLifecycleOwner) {
            val toSearchAll = SearchFragmentDirections.actionSearchNavFragmentToSearchAllFragment(it,"tv")
            findNavController().navigate(toSearchAll)
        }
    }

    private fun showMovieDetail(item: Movie) {
        viewLifecycleOwner.lifecycleScope.launch {
            val movie = detailViewModel.getMovieDetail(item.id)
            val MovieToDetail = SearchFragmentDirections.actionSearchNavFragmentToDetailFragment(
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
            val TVToDetail = SearchFragmentDirections.actionSearchNavFragmentToDetailFragment(
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
            findNavController().navigate(TVToDetail)
        }

    }


}