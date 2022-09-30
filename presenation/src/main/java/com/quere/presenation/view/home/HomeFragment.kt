package com.quere.presenation.view.home


import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.BackgroundColorSpan
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.quere.domain.model.common.Detail
import com.quere.domain.model.movie.Movie
import com.quere.domain.model.tv.TVshow
import com.quere.presenation.AppConstants
import com.quere.presenation.R
import com.quere.presenation.databinding.FragmentHomeBinding
import com.quere.presenation.base.BaseFragment
import com.quere.presenation.view.adapter.HorizontalItemDecorator
import com.quere.presenation.view.adapter.MoviePagingAdapter
import com.quere.presenation.view.adapter.TVPagingAdapter
import com.quere.presenation.viewmodel.DetailViewModel
import com.quere.presenation.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val homeViewModel: HomeViewModel by activityViewModels()
    private val detailViewModel : DetailViewModel by activityViewModels()

    override fun initView() {

        observeViewModel()

        binding.apply {

            viewmodel = homeViewModel

            getMovieRecyclerView(recyclerViewHomeMovieNowplaying, AppConstants.NOW_PLAYING)
            getMovieRecyclerView(recyclerViewHomeMovieToprated, AppConstants.TOP_RATED)
            getMovieRecyclerView(recyclerViewHomeMoviePopular, AppConstants.POPULAR)
            getMovieRecyclerView(recyclerViewHomeMovieUpcoming, AppConstants.UPCOMING)

            getTVRecyclerView(recyclerViewHomeTvOnTheAir, AppConstants.ONTHEAIR)
            getTVRecyclerView(recyclerViewHomeTvTopraged, AppConstants.TOP_RATED)
            getTVRecyclerView(recyclerViewHomeTvPopular, AppConstants.POPULAR)
        }
    }

    private fun observeViewModel() {

        homeViewModel.fragmentMovieGenre.observe(viewLifecycleOwner) {
            if (it.consumed) return@observe
            showMovieGenre()
            it.consume()
        }

        homeViewModel.fragmentTVGenre.observe(viewLifecycleOwner) {
            if (it.consumed) return@observe
            showTVGenre()
            it.consume()
        }

    }

    private fun getMovieRecyclerView(recyclerView: RecyclerView, type: String) {
        val movieAdapter: MoviePagingAdapter by lazy {
            MoviePagingAdapter(
                ItemClick = {
                    showMovieDetail(it)
                }
            )
        }
        recyclerView.adapter = movieAdapter

        recyclerView.addItemDecoration(HorizontalItemDecorator(10))
        collectMovieFlow(type, movieAdapter)

    }

    private fun getTVRecyclerView(recyclerView: RecyclerView, type: String) {

        val tvAdapter: TVPagingAdapter by lazy {
            TVPagingAdapter(
                ItemClick = {
                    showTVDetail(it)
                }
            )
        }

        recyclerView.adapter = tvAdapter
        recyclerView.addItemDecoration(HorizontalItemDecorator(10))
        collectTVFlow(type, tvAdapter)

    }

    private fun collectMovieFlow(type: String, movieAdapter: MoviePagingAdapter) {


        when (type) {
            AppConstants.NOW_PLAYING -> {
                viewLifecycleOwner.lifecycleScope.launch {
                    homeViewModel.now_movie().collectLatest { movieList ->
                        movieAdapter.submitData(movieList)

                    }
                }
            }

            AppConstants.TOP_RATED -> {
                viewLifecycleOwner.lifecycleScope.launch {
                    homeViewModel.top_movie().collectLatest { movieList ->
                        movieAdapter.submitData(movieList)
                    }
                }

            }
            AppConstants.UPCOMING -> {
                viewLifecycleOwner.lifecycleScope.launch {
                    homeViewModel.upcoming_movie().collectLatest { movieList ->
                        movieAdapter.submitData(movieList)
                    }
                }
            }
            AppConstants.POPULAR -> {
                viewLifecycleOwner.lifecycleScope.launch {
                    homeViewModel.popular_movie().collectLatest { movieList ->
                        movieAdapter.submitData(movieList)
                    }
                }
            }
        }
    }

    private fun collectTVFlow(type: String, tvAdapter: TVPagingAdapter) {


        when (type) {
            AppConstants.ONTHEAIR -> {
                viewLifecycleOwner.lifecycleScope.launch {
                    homeViewModel.onair_tv().collectLatest { tvList ->
                        tvAdapter.submitData(tvList)
                    }
                }
            }

            AppConstants.TOP_RATED -> {
                viewLifecycleOwner.lifecycleScope.launch {
                    homeViewModel.top_tv().collectLatest { tvList ->
                        tvAdapter.submitData(tvList)
                    }
                }

            }
            AppConstants.POPULAR -> {
                viewLifecycleOwner.lifecycleScope.launch {
                    homeViewModel.popular_tv().collectLatest { tvList ->
                        tvAdapter.submitData(tvList)
                    }
                }
            }

        }
    }


    private fun showMovieDetail(item: Movie) {
        viewLifecycleOwner.lifecycleScope.launch {
            val movie = detailViewModel.getMovieDetail(item.id)
            val MovieToDetail = HomeFragmentDirections.actionHomeFragmentToDetailFragment(
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
            val TvToDetail = HomeFragmentDirections.actionHomeFragmentToDetailFragment(
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

    private fun showMovieGenre() {
        val moveGenre = HomeFragmentDirections.actionHomeFragmentToMovieGenreFragment()
        findNavController().navigate(moveGenre)
    }

    private fun showTVGenre() {
        val tvGenre = HomeFragmentDirections.actionHomeFragmentToTVGenreFragment()
        findNavController().navigate(tvGenre)
    }


}
