package com.quere.moodra.view.home


import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
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
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel by viewModels<HomeViewModel>()
    private var doubleBackToExit = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)



        (requireActivity() as AppCompatActivity).supportActionBar?.hide()

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(backPressedDispatcher)
    }

    protected fun navigateUp() {

        if (doubleBackToExit==true) {
            requireActivity().finishAffinity()
        }

        if(doubleBackToExit==false){
            Toast.makeText(requireContext(), "종료하시려면 뒤로가기를 한번 더 눌러주세요.", Toast.LENGTH_SHORT)
                .show()
            doubleBackToExit = true
            runDelayed(1500L) {
                doubleBackToExit = false

            }
        }
    }


    fun runDelayed(millis: Long, function: () -> Unit) {
        Handler(Looper.getMainLooper()).postDelayed(function, millis)
    }

    private val backPressedDispatcher = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            navigateUp()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            getMovieRecycler(movieNowplayingRecyclerview, AppConstants.NOW_PLAYING)
            getMovieRecycler(moviePopularRecyclerview, AppConstants.POPULAR)
            getMovieRecycler(movieTopratedRecyclerview, AppConstants.TOP_RATED)
            getMovieRecycler(movieUpcomingRecyclerview, AppConstants.UPCOMING)

            getTVshowRecycler(tvOntheairRecyclerview, AppConstants.ONTHEAIR)
            getTVshowRecycler(tvPopularRecyclerview, AppConstants.POPULAR)
            getTVshowRecycler(tvTopratedRecyclerview, AppConstants.TOP_RATED)

            movies.setOnClickListener(object : View.OnClickListener {
                override fun onClick(p0: View?) {
                    findNavController().navigate(R.id.nav_home_MovieGenre)
                }

            })

            tvShow.setOnClickListener(object : View.OnClickListener {
                override fun onClick(p0: View?) {
                    findNavController().navigate(R.id.nav_home_TVshowGenre)
                }

            })

        }


    }

    private fun moveMovieToDetail(movie: Movie) {


        val MovieToDetail = HomeFragmentDirections.actionNavHomeHomeToNavHomeDetail(

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

        findNavController().navigate(MovieToDetail)


    }


    private fun moveTVToDetail(tvshow: TVshow) {


        val TvToDetail = HomeFragmentDirections.actionNavHomeHomeToNavHomeDetail(
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
        findNavController().navigate(TvToDetail)


    }

    private fun getMovieRecycler(
        recyclerView: RecyclerView,
        type: String
    ) {
        val adapter = MovieAdapter({ movie -> moveMovieToDetail(movie) },
            { movie -> moveMovieToDetail(movie) })

        adapter.addLoadStateListener { loadState ->
            binding.apply {

                if (loadState.refresh is LoadState.Loading) {
                    homeScrollview.visibility = View.GONE
                    homeStl.visibility = View.VISIBLE

                }

                if(loadState.refresh is LoadState.NotLoading){

                    homeStl.visibility = View.GONE
                    homeScrollview.visibility = View.VISIBLE


                }
            }
        }
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


    private fun getTVshowRecycler(
        recyclerView: RecyclerView,
        type: String
    ) {
        val adapter = TVshowAdapter({ TVshow -> moveTVToDetail(TVshow) },
            { TVshow -> moveTVToDetail(TVshow) })

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