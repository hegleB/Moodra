package com.quere.presenation.view.home


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
import com.quere.presenation.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val homeViewModel: HomeViewModel by activityViewModels()

    override fun initView() {

        binding.apply {

            movies.setOnClickListener {HomeToMovieGenre()}
            tvShow.setOnClickListener { HomeToTVGenre() }

            getMovieRecyclerView(movieNowplayingRecyclerview, AppConstants.NOW_PLAYING)
            getMovieRecyclerView(movieTopratedRecyclerview, AppConstants.TOP_RATED)
            getMovieRecyclerView(moviePopularRecyclerview, AppConstants.POPULAR)
            getMovieRecyclerView(movieUpcomingRecyclerview, AppConstants.UPCOMING)

            geTVRecyclerView(tvOntheairRecyclerview, AppConstants.ONTHEAIR)
            geTVRecyclerView(tvTopratedRecyclerview, AppConstants.TOP_RATED)
            geTVRecyclerView(tvPopularRecyclerview, AppConstants.POPULAR)
        }
    }


    private fun getMovieRecyclerView(recyclerView: RecyclerView, type: String) {
        val movieAdapter: MoviePagingAdapter by lazy {
            MoviePagingAdapter(
                ItemClick = { doOnClick(it) }
            )
        }
        recyclerView.adapter = movieAdapter

        recyclerView.addItemDecoration(HorizontalItemDecorator(10))
        getMovieFlow(type, movieAdapter)

    }

    private fun geTVRecyclerView(recyclerView: RecyclerView, type: String) {

        val tvAdapter: TVPagingAdapter by lazy {
            TVPagingAdapter(
                ItemClick = { doOnClick(it) }
            )
        }

        recyclerView.adapter = tvAdapter
        recyclerView.addItemDecoration(HorizontalItemDecorator(10))
        getTVFlow(type, tvAdapter)

    }

    private fun getMovieFlow(type: String, movieAdapter: MoviePagingAdapter) {


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

    private fun getTVFlow(type: String, tvAdapter: TVPagingAdapter) {


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

    private fun doOnClick(item: Any) {
        when (item) {

            is Movie -> {
                val MovieToDetail = HomeFragmentDirections.actionHomeFragmentToDetailFragment(
                    Detail(
                        true,
                        item.title,
                        "",
                        listOf(),
                        item.id,
                        item.overview,
                        false,
                        item.poster_path,
                        item.backdrop_path,
                        item.release_date,
                        item.runtime,
                        item.video,
                        item.vote_average
                    )
                )
                findNavController().navigate(MovieToDetail)
            }
            is TVshow -> {
                val TvToDetail = HomeFragmentDirections.actionHomeFragmentToDetailFragment(
                    Detail(
                        false,
                        "",
                        item.name!!,
                        listOf(),
                        item.id,
                        item.overview!!,
                        false,
                        item.poster_path!!,
                        item.backdrop_path!!,
                        item.first_air_date!!,
                        0,
                        item.video,
                        item.vote_average!!
                    )
                )
                findNavController().navigate(TvToDetail)
            }

        }
    }

    private fun HomeToMovieGenre(){
        val moveGenre = HomeFragmentDirections.actionHomeFragmentToMovieGenreFragment()

        findNavController().navigate(moveGenre)
    }

    private fun HomeToTVGenre(){
        val tvGenre = HomeFragmentDirections.actionHomeFragmentToTVGenreFragment()

        findNavController().navigate(tvGenre)
    }


}
