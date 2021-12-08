package com.quere.moodra.view


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.quere.moodra.AppConstants
import com.quere.moodra.R
import com.quere.moodra.adapter.HorizontalItemDecorator
import com.quere.moodra.adapter.MovieAdapter
import com.quere.moodra.adapter.TVshowAdapter
import com.quere.moodra.databinding.FragmentHomeBinding
import com.quere.moodra.retrofit.Movie
import com.quere.moodra.retrofit.TVshow
import com.quere.moodra.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel by viewModels<HomeViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)



        (requireActivity() as AppCompatActivity).supportActionBar?.hide()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            MovieRecycler(movieNowplayingRecyclerview, AppConstants.NOW_PLAYING)
            MovieRecycler(moviePopularRecyclerview, AppConstants.POPULAR)
            MovieRecycler(movieTopratedRecyclerview, AppConstants.TOP_RATED)
            MovieRecycler(movieUpcomingRecyclerview, AppConstants.UPCOMING)

            TVshowRecycler(tvOntheairRecyclerview, AppConstants.ONTHEAIR)
            TVshowRecycler(tvPopularRecyclerview, AppConstants.POPULAR)
            TVshowRecycler(tvTopratedRecyclerview, AppConstants.TOP_RATED)

            movies.setOnClickListener(object : View.OnClickListener {
                override fun onClick(p0: View?) {
                    findNavController().navigate(R.id.movieFragment)
                }

            })

            tvShow.setOnClickListener(object : View.OnClickListener {
                override fun onClick(p0: View?) {
                    findNavController().navigate(R.id.TVshowFragment)
                }

            })

        }


    }

    private fun MovieDialog(movie: Movie) {


        val direction = HomeFragmentDirections.actionHomeFragmentToDetailFragment(

            "movie",
            movie.title ?: "제목 없음",
            movie.id,
            movie.overview ?: "줄거리 없음",
            false,
            movie.poster_path ?: "이미지 없음",
            movie.backdrop_path ?: "이미지 없음",
            movie.release_date ?: "개봉날짜 없음",
            movie.vote_average ?: "0",

        )

        findNavController().navigate(direction)


    }


    private fun TVDialog(tvshow: TVshow) {


        val direction = HomeFragmentDirections.actionHomeFragmentToDetailFragment(
            "tv",
            tvshow.name ?: "이름 엾음",
            tvshow.id,
            tvshow.overview ?: "줄거리 없음",
            false,
            tvshow.poster_path ?: "이미지 없음",
            tvshow.backdrop_path ?: "이미지 없음",
            tvshow.first_air_date ?: "방영날짜 없음",
            tvshow.vote_average ?: "0",
        )
        findNavController().navigate(direction)


    }

    private fun MovieRecycler(
        recyclerView: RecyclerView,
        type: String
    ) {
        val adapter = MovieAdapter({ movie -> MovieDialog(movie) },
            { movie -> MovieDialog(movie) })
        adapter.notifyDataSetChanged()
        when (type) {
            AppConstants.NOW_PLAYING -> viewLifecycleOwner.lifecycleScope.launch {
                viewModel.now_movies().collectLatest {
                    adapter.submitData(it)

                }
            }

            AppConstants.UPCOMING -> viewLifecycleOwner.lifecycleScope.launch {
                viewModel.upcoming_movies().collectLatest {
                    adapter.submitData(it)
                }
            }

            AppConstants.TOP_RATED -> viewLifecycleOwner.lifecycleScope.launch {
                viewModel.top_movies().collectLatest {
                    adapter.submitData(it)
                }
            }

            else -> viewLifecycleOwner.lifecycleScope.launch {
                viewModel.popular_movies().collectLatest {
                    adapter.submitData(it)
                }
            }
        }

        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(HorizontalItemDecorator(10))
        recyclerView.setHasFixedSize(true)

    }


    private fun TVshowRecycler(
        recyclerView: RecyclerView,
        type: String
    ) {
        val adapter = TVshowAdapter({ TVshow -> TVDialog(TVshow) },
            { TVshow -> TVDialog(TVshow) })

        when (type) {
            AppConstants.ONTHEAIR -> viewLifecycleOwner.lifecycleScope.launch {
                viewModel.onair_tv().collectLatest {
                    adapter.submitData(it)

                }
            }

            AppConstants.TOP_RATED -> viewLifecycleOwner.lifecycleScope.launch {
                viewModel.top_tv().collectLatest {
                    adapter.submitData(it)
                }
            }

            else -> viewLifecycleOwner.lifecycleScope.launch {
                viewModel.popular_tv().collectLatest {
                    adapter.submitData(it)
                }
            }
        }

        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(HorizontalItemDecorator(10))
        recyclerView.setHasFixedSize(true)
    }
}