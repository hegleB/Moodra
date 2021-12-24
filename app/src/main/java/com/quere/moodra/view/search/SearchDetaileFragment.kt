package com.quere.moodra.view.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.quere.moodra.AppConstants
import com.quere.moodra.R
import com.quere.moodra.adapter.MovieSearchDetailAdapter
import com.quere.moodra.adapter.SearchDetailLoadStateAdapter
import com.quere.moodra.adapter.TVSearchDetailAdapter
import com.quere.moodra.databinding.FragmentSearchDetailBinding
import com.quere.moodra.retrofit.MovieSearch
import com.quere.moodra.retrofit.TVshowSearch
import com.quere.moodra.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class SearchDetaileFragment : Fragment() {


    private lateinit var binding: FragmentSearchDetailBinding
    private val viewModel by viewModels<SearchViewModel>()

    val SearchDetailArgs by navArgs<SearchDetaileFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(requireContext()),
            R.layout.fragment_search_detail,
            container,
            false
        )

        when (SearchDetailArgs.type) {
            AppConstants.MOVIE -> {
                val adapter = MovieSearchDetailAdapter({ movies -> moveMovieSearchToDetail(movies) },
                    { movies -> moveMovieSearchToDetail(movies) })


                binding.apply {

                    val gridLayout = GridLayoutManager(requireContext(), 3)

                    gridLayout.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                        override fun getSpanSize(position: Int): Int {
                            var viewType = adapter.getItemViewType(position)
                            return if (viewType == R.layout.item_movie_search_detail) 1
                            else 3
                        }

                    }

                    searchDetaileRecyclerview.layoutManager =
                        gridLayout


                    searchDetaileRecyclerview.setHasFixedSize(true)


                    searchDetaileRecyclerview.adapter = adapter.withLoadStateFooter(
                        footer= SearchDetailLoadStateAdapter { adapter.retry() }

                    )


                }


                try {
                    viewLifecycleOwner.lifecycleScope.launch {


                        viewModel.getMoviesDetail(SearchDetailArgs.query).collectLatest {
                            adapter.submitData(viewLifecycleOwner.lifecycle, it)
                        }

                    }


                } catch (e: Exception) {

                }
                adapter.addLoadStateListener { loadState ->
                    binding.apply {

                        if (loadState.refresh is LoadState.Loading) {
                            searchDetailStl.visibility = View.VISIBLE
                        }

                        if (loadState.refresh is LoadState.NotLoading){
                            searchDetailStl.visibility = View.GONE
                        }
                    }
                }
            }

            AppConstants.TV -> {

                val adapter = TVSearchDetailAdapter({ tvs -> moveTVSearchToDetail(tvs) },
                    { tvs -> moveTVSearchToDetail(tvs) })

                binding.apply {

                    val gridLayout = GridLayoutManager(requireContext(), 3)

                    gridLayout.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                        override fun getSpanSize(position: Int): Int {
                            var viewType = adapter.getItemViewType(position)
                            return if (viewType == R.layout.item_tv_search_detail) 1
                            else 3
                        }

                    }

                    searchDetaileRecyclerview.layoutManager =
                        gridLayout

                    searchDetaileRecyclerview.adapter = adapter.withLoadStateFooter(
                        footer= SearchDetailLoadStateAdapter { adapter.retry() }

                    )


                }

                try {
                    viewLifecycleOwner.lifecycleScope.launch {

                        viewModel.getTvshowDetail(SearchDetailArgs.query).collectLatest {
                            adapter.submitData(viewLifecycleOwner.lifecycle, it)
                        }
                    }
                } catch (e: Exception) {

                }
                adapter.addLoadStateListener { loadState ->
                    binding.apply {

                        if (loadState.refresh is LoadState.Loading) {
                            searchDetailStl.visibility = View.VISIBLE
                        }

                        if (loadState.refresh is LoadState.NotLoading){
                            searchDetailStl.visibility = View.GONE
                        }
                    }
                }

            }
        }



        binding.apply {
            searchDetailToolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_24)

            searchDetailToolbar.setNavigationOnClickListener(object : View.OnClickListener {
                override fun onClick(p0: View?) {
                    findNavController().navigateUp()
                }

            })

            searchDetailText.setText(SearchDetailArgs.query)


        }




        return binding.root
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {

        }
        return super.onContextItemSelected(item)
    }

    private fun moveMovieSearchToDetail(movie_search: MovieSearch) {

        val MovieSearchToDetail =
            SearchDetaileFragmentDirections.actionNavSearchSearchDetatilToNavSearchDetail(
                "movie",
                movie_search.title ?: "제목 없음",
                movie_search.id!!,
                movie_search.overview ?: "줄거리 없음",
                movie_search.adult ?: false,
                movie_search.poster_path ?: "이미지 없음",
                movie_search.backdrop_path ?: "이미지 없음",
                movie_search.release_date ?: "개봉날짜 없음",
                movie_search.vote_average ?: "0"
            )

        findNavController().navigate(MovieSearchToDetail)


    }

    private fun moveTVSearchToDetail(tv_search: TVshowSearch) {

        val TVSearchToDetail =
            SearchDetaileFragmentDirections.actionNavSearchSearchDetatilToNavSearchDetail(
                "tv",
                tv_search.name ?: "제목 없음",
                tv_search.id!!,
                tv_search.overview ?: "줄거리 없음",
                false,
                tv_search.poster_path ?: "이미지 없음",
                tv_search.backdrop_path ?: "이미지 없음",
                tv_search.first_air_date ?: "개봉날짜 없음",
                tv_search.vote_average ?: "0"
            )

        findNavController().navigate(TVSearchToDetail)


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // handle back button
        requireActivity().onBackPressedDispatcher.addCallback(this) {

            findNavController().navigateUp()
        }

    }

}