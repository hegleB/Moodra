package com.quere.moodra.view

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
import androidx.recyclerview.widget.RecyclerView
import com.quere.moodra.R
import com.quere.moodra.adapter.MovieGenreDetailAdapter
import com.quere.moodra.databinding.FragmentMovieGenreDetailBinding
import com.quere.moodra.retrofit.Movie
import com.quere.moodra.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_movie_genre_detail.*
import kotlinx.android.synthetic.main.fragment_search_movie.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.lang.Exception

@AndroidEntryPoint
class MovieGenreDetailFragment : Fragment() {

    private lateinit var binding: FragmentMovieGenreDetailBinding
    private val viewModel by viewModels<MovieViewModel>()
    val twoDepArgs by navArgs<MovieGenreDetailFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_genre_detail, container, false)
        setHasOptionsMenu(true)

        val adapter = MovieGenreDetailAdapter({ movies -> MovieDialog(movies) },
            { movies -> MovieDialog(movies) })


        binding.apply {
            movieDetaileRecyclerview.setHasFixedSize(true)
            movieDetaileRecyclerview.layoutManager = GridLayoutManager(context,2)
            movieDetaileRecyclerview.itemAnimator = null
            movieDetaileRecyclerview.adapter = adapter

            movieDetailToolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_24)
            movieDetailToolbar.setNavigationOnClickListener(object : View.OnClickListener{
                override fun onClick(p0: View?) {
                    findNavController().navigateUp()
                }

            })

            movieDetailText.setText(twoDepArgs.genre)


        }

        adapter.addLoadStateListener { loadState ->
            binding.apply {

                // empty view
                if (loadState.source.refresh is LoadState.NotLoading &&
                    loadState.append.endOfPaginationReached &&
                    adapter.itemCount < 1
                ) {

                } else {

                }
            }
        }


        try {
            viewLifecycleOwner.lifecycleScope.launch {

                viewModel.movies_detail(twoDepArgs.genre).collectLatest {

                    adapter.submitData(viewLifecycleOwner.lifecycle,it)
                    adapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
                }
            }
        } catch (e: Exception){

        }



        return binding.root
    }

    private fun MovieDialog(movie: Movie) {

        val direction = MovieGenreDetailFragmentDirections.actionMovieGenreDetailFragmentToDetailFragment(
            "movie",
            movie.title ?: "제목 없음",
            movie.id,
            movie.overview ?: "줄거리 없음",
            false,
            movie.poster_path ?: "이미지 없음",
            movie.backdrop_path ?: "이미지 없음",
            movie.release_date ?: "개봉날짜 없음",
            movie.vote_average?: "0"
        )
        findNavController().navigate(direction)


    }



}