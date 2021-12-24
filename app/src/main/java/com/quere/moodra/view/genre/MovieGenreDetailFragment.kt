package com.quere.moodra.view.genre

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.quere.moodra.AppConstants
import com.quere.moodra.R
import com.quere.moodra.adapter.MovieGenreDetailAdapter
import com.quere.moodra.adapter.SearchDetailLoadStateAdapter
import com.quere.moodra.databinding.FragmentMovieGenreDetailBinding
import com.quere.moodra.retrofit.Movie
import com.quere.moodra.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.lang.Exception

@AndroidEntryPoint
class MovieGenreDetailFragment : Fragment() {

    private lateinit var binding: FragmentMovieGenreDetailBinding
    private val viewModel by viewModels<MovieViewModel>()
    val movieGenreDepArgs by navArgs<MovieGenreDetailFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_movie_genre_detail,
            container,
            false
        )
        setHasOptionsMenu(true)

        val adapter = MovieGenreDetailAdapter({ movies -> moveMovieGenreDetailToDetail(movies) },
            { movies -> moveMovieGenreDetailToDetail(movies) })


        binding.apply {
            movieDetaileRecyclerview.setHasFixedSize(true)

            val gridLayout = GridLayoutManager(requireContext(), 3)

            gridLayout.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    var viewType = adapter.getItemViewType(position)
                    return if (viewType == R.layout.item_movie_genre_detail) 1
                    else 3
                }

            }

            movieDetaileRecyclerview.layoutManager = gridLayout

            movieDetaileRecyclerview.adapter = adapter.withLoadStateFooter(
                SearchDetailLoadStateAdapter { adapter.retry() }
            )

            movieDetailToolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_24)
            movieDetailToolbar.setNavigationOnClickListener(object : View.OnClickListener {
                override fun onClick(p0: View?) {
                    findNavController().navigateUp()
                }

            })

            movieDetailText.setText(movieGenreDepArgs.movieGenre)


        }

        adapter.addLoadStateListener { loadState ->
            binding.apply {

                if (loadState.refresh is LoadState.Loading) {
                    movieGenreDetailStl.visibility = View.VISIBLE
                }

                if (loadState.refresh is LoadState.NotLoading){
                    movieGenreDetailStl.visibility = View.GONE
                }
            }
        }


        try {
            viewLifecycleOwner.lifecycleScope.launch {

                viewModel.movies_detail(movieGenreDepArgs.movieGenre).collectLatest {

                    adapter.submitData(viewLifecycleOwner.lifecycle, it)
                }
            }
        } catch (e: Exception) {

        }

        return binding.root
    }

    private fun moveMovieGenreDetailToDetail(movie: Movie) {

        val MovieGenreDetailToDetail =
            MovieGenreDetailFragmentDirections.actionNavHomeMovieGenreDeatilToNavHomeDetail(
                AppConstants.MOVIE,
                movie.title ?: "제목 없음",
                movie.id,
                movie.overview ?: "줄거리 없음",
                false,
                movie.poster_path ?: "이미지 없음",
                movie.backdrop_path ?: "이미지 없음",
                movie.release_date ?: "개봉날짜 없음",
                movie.vote_average ?: "0"
            )
        findNavController().navigate(MovieGenreDetailToDetail)


    }


}