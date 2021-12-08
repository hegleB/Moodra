package com.quere.moodra.view


import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity

import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.quere.moodra.R
import com.quere.moodra.adapter.*
import com.quere.moodra.databinding.FragmentSearchBinding

import com.quere.moodra.databinding.FragmentSearchTvBinding
import com.quere.moodra.retrofit.MovieSearch
import com.quere.moodra.retrofit.TVshowSearch
import com.quere.moodra.viewmodel.SearchViewModel

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var binding1: FragmentSearchTvBinding
    private lateinit var direction : NavDirections


    private val viewModel by viewModels<SearchViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            LayoutInflater.from(requireContext()),
            R.layout.fragment_search,
            container,
            false
        )


        val movieAdapter = MovieSearchDetailAdapter({ MovieSearch -> MovieSearchDialog(MovieSearch) },
            { MovieSearch -> MovieSearchDialog(MovieSearch) })



        val tvAdapter = TVSearchDetailAdapter({ TVSearch -> TVshowSearchDialog(TVSearch) },
            { TVSearch -> TVshowSearchDialog(TVSearch) })

        binding.apply {
            searchMovieRecyclerview.setHasFixedSize(true)
            searchMovieRecyclerview.itemAnimator = null
            searchMovieRecyclerview.adapter = movieAdapter.withLoadStateHeaderAndFooter(
                header = MovieSearchLoadStateAdapter { movieAdapter.retry() },
                footer = MovieSearchLoadStateAdapter { movieAdapter.retry() }
            )

            searchTvRecyclerview.setHasFixedSize(true)
            searchTvRecyclerview.itemAnimator = null
            searchTvRecyclerview.adapter = tvAdapter.withLoadStateHeaderAndFooter(
                header = MovieSearchLoadStateAdapter { tvAdapter.retry() },
                footer = MovieSearchLoadStateAdapter { tvAdapter.retry() }
            )


            searchView.setIconifiedByDefault(false)


            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    viewModel.setQuery(query!!)
                    searchView.clearFocus()
                    noSearch.visibility=View.GONE
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if(newText==""){
                        noSearch.visibility=View.VISIBLE
                        viewModel.setQuery(newText!!)
                    }
                    return true
                }

            })
            
            
            viewModel.query.observe(viewLifecycleOwner, Observer {
                if(it!=""){
                    noSearch.visibility=View.GONE
                }
            })





            movieResultDetail.setOnClickListener(object : View.OnClickListener{
                override fun onClick(p0: View?) {
                    try{
                        viewModel.query.observe(viewLifecycleOwner, Observer {
                            direction = SearchFragmentDirections.actionSearchFragmentToSearchDetaileFragment(it,"movie")
                        })

                        findNavController().navigate(direction)
                    } catch (e:Exception){
                        direction = SearchFragmentDirections.actionSearchFragmentToSearchDetaileFragment("","movie")
                        findNavController().navigate(direction)
                    }
                }

            })

            tvResultDetail.setOnClickListener(object : View.OnClickListener{
                override fun onClick(p0: View?) {
                    try{
                        viewModel.query.observe(viewLifecycleOwner, Observer {
                            direction = SearchFragmentDirections.actionSearchFragmentToSearchDetaileFragment(it,"tv")
                        })
                        findNavController().navigate(direction)
                    } catch (e:Exception){
                        direction = SearchFragmentDirections.actionSearchFragmentToSearchDetaileFragment("","tv")
                        findNavController().navigate(direction)
                    }
                }

            })


        }



        movieAdapter.addLoadStateListener { loadState ->
            binding.apply {

                // empty view
                if (loadState.source.refresh is LoadState.NotLoading &&
                    loadState.append.endOfPaginationReached &&
                    movieAdapter.itemCount < 1
                ) {
                    searchMovieRecyclerview.isVisible = false

                } else {

                }
            }
        }

        tvAdapter.addLoadStateListener { loadState ->
            binding.apply {

                // empty view
                if (loadState.source.refresh is LoadState.NotLoading &&
                    loadState.append.endOfPaginationReached &&
                    movieAdapter.itemCount < 1
                ) {
                    searchTvRecyclerview.isVisible = false

                } else {

                }
            }
        }

        viewModel.query.observe(viewLifecycleOwner, Observer {

            MovieRecycler(it, binding.searchMovieRecyclerview)
            TvRecycler(it, binding.searchTvRecyclerview)

        })


        (requireActivity() as AppCompatActivity).supportActionBar?.hide()

        return binding.root
    }


    private fun MovieSearchDialog(movie_search: MovieSearch) {

        val direction = SearchFragmentDirections.actionSearchFragmentToDetailFragment(
            "movie",
            movie_search.title ?: "제목 없음",
            movie_search.id!!,
            movie_search.overview ?: "줄거리 없음",
            movie_search.adult ?: false,
            movie_search.poster_path ?: "이미지 없음",
            movie_search.backdrop_path ?: "이미지 없음",
            movie_search.release_date ?: "개봉날짜 없음",
            movie_search.vote_average?: "0"
        )

        findNavController().navigate(direction)


    }

    private fun TVshowSearchDialog(tv_search: TVshowSearch) {


        val direction = SearchFragmentDirections.actionSearchFragmentToDetailFragment(
            "tv",
            tv_search.name ?: "제목 없음",
            tv_search.id,
            tv_search.overview ?: "줄거리 없음",
            false,
            tv_search.poster_path ?: "이미지 없음",
            tv_search.poster_path ?: "이미지 없음",
            tv_search.first_air_date ?: "방영날짜 없음",
            tv_search.vote_average?: "0"
        )
        findNavController().navigate(direction)


    }

    private fun MovieRecycler(query: String,recyclerView: RecyclerView) {

        val adapter = MovieSearchDataAdapter({ movie -> MovieSearchDialog(movie) },
            { movie -> MovieSearchDialog(movie) })
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getMovies(query).collectLatest {

                adapter.submitData(it)
            }
        }
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)

    }

    private fun TvRecycler(query: String, recyclerView: RecyclerView) {

        val adapter = TvSearchDataAdapter({ tv -> TVshowSearchDialog(tv) },
            { tv -> TVshowSearchDialog(tv) })

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getTvshow(query).collectLatest {
                adapter.submitData(it)
            }

        }

        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
    }

}