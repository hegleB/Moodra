package com.quere.moodra.view.genre


import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.quere.moodra.AppConstants
import com.quere.moodra.R
import com.quere.moodra.adapter.HorizontalItemDecorator
import com.quere.moodra.adapter.MovieGenreAdatper
import com.quere.moodra.adapter.MovieViewPagerAdapter
import com.quere.moodra.databinding.FragmentMovieBinding
import com.quere.moodra.retrofit.Movie
import com.quere.moodra.viewmodel.HomeViewModel
import com.quere.moodra.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_movie.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieGenreFragment : Fragment() {


    private lateinit var binding: FragmentMovieBinding
    private val viewModel by viewModels<MovieViewModel>()
    private val viewModel1 by viewModels<HomeViewModel>()
    private var viewpager_pos = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie, container, false)

        binding.tvShowTv.setOnClickListener({
            findNavController().navigate(R.id.action_nav_home_MovieGenre_to_nav_home_TVshowGenre)
        })

        binding.home.setOnClickListener({
            findNavController().navigate(R.id.action_nav_home_MovieGenre_to_nav_home_Home)
        })

        (requireActivity() as AppCompatActivity).supportActionBar?.hide()

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.apply {
            getMovieGenreRecycler(movieActionActionRecyclerview, AppConstants.ACTION)
            getMovieGenreRecycler(movieFantasyRecyclerview, AppConstants.FANTASY)
            getMovieGenreRecycler(movieAnimationRecyclerview, AppConstants.ANIMATION)
            getMovieGenreRecycler(movieComedyRecyclerview, AppConstants.COMEDY)
            getMovieGenreRecycler(movieMusicRecyclerview, AppConstants.MUSIC)
            getMovieGenreRecycler(movieRomanceRecyclerview, AppConstants.ROMANCE)
            getMovieGenreRecycler(movieCrimeRecyclerview, AppConstants.CRIME)
            getMovieGenreRecycler(movieMysteryRecyclerview, AppConstants.MYSTERY)
            getMovieGenreRecycler(movieHorrorRecyclerview, AppConstants.HORROR)

            getMovieViewPager(moviePopularViewpager)
            getMovieViewPagerCurrentItem(moviePopularViewpager)

            moveToMovieGenreDetail(movie_action_detail, "액션")
            moveToMovieGenreDetail(movie_fantasy_detail, "판타지")
            moveToMovieGenreDetail(movie_animation_detail, "애니메이션")
            moveToMovieGenreDetail(movie_comedy_detail, "코미디")
            moveToMovieGenreDetail(movie_music_detail, "뮤직")
            moveToMovieGenreDetail(movie_romance_detail, "로맨스")
            moveToMovieGenreDetail(movie_crime_detail, "범죄")
            moveToMovieGenreDetail(movie_mystery_detail, "미스테리")
            moveToMovieGenreDetail(movie_horror_detail, "호러")
        }

        move_scrollview.setOnScrollChangeListener(object :
            NestedScrollView.OnScrollChangeListener {
            override fun onScrollChange(
                v: NestedScrollView?,
                scrollX: Int,
                scrollY: Int,
                oldScrollX: Int,
                oldScrollY: Int
            ) {
                if (scrollY >= 0) {
                    if (scrollY < 1300) {  //적당한 높이
                        movie_actionbar.setBackgroundColor(Color.BLACK)
                        movie_actionbar.getBackground()
                            .setAlpha((scrollY.toFloat() / 1300.toFloat() * 255.toFloat()).toInt())
                    }
                }
            }

        })
    }


    private fun moveMovieGenreToDetail(movie: Movie) {

        val MovieGenreToDetail = MovieGenreFragmentDirections.actionNavHomeMovieGenreToNavHomeDetail(
            AppConstants.MOVIE,
            movie.title ?: "제목 없음",
            movie.id,
            movie.overview ?: "줄거리 없음",
            false,
            movie.poster_path ?: "이미지 없음",
            movie.backdrop_path ?: "이미지 없음",
            movie.release_date ?: "개봉날짜 없음",
            movie.vote_average ?: "0",
        )
        findNavController().navigate(MovieGenreToDetail)


    }

    private fun getMovieGenreRecycler(recyclerView: RecyclerView, type: String) {
        val adapter = MovieGenreAdatper({ Movie -> moveMovieGenreToDetail(Movie) },
            { Movie -> moveMovieGenreToDetail(Movie) })

        adapter.addLoadStateListener { loadState ->
            binding.apply {

                if (loadState.refresh is LoadState.Loading) {
                    moveScrollview.visibility = View.GONE
                    movieStl.visibility = View.VISIBLE
                } else {

                    movieStl.visibility = View.GONE
                    moveScrollview.visibility = View.VISIBLE

                }
            }
        }

        when (type) {
            AppConstants.ACTION -> viewLifecycleOwner.lifecycleScope.launch {
                viewModel.action_movies().collectLatest {
                    adapter.submitData(lifecycle, it)
                }
            }

            AppConstants.FANTASY -> viewLifecycleOwner.lifecycleScope.launch {
                viewModel.fantasy_movies().collectLatest {
                    adapter.submitData(it)
                }
            }

            AppConstants.ANIMATION -> viewLifecycleOwner.lifecycleScope.launch {
                viewModel.animation_movies().collectLatest {
                    adapter.submitData(it)
                }
            }

            AppConstants.COMEDY -> viewLifecycleOwner.lifecycleScope.launch {
                viewModel.comedy_movies().collectLatest {
                    adapter.submitData(it)
                }
            }

            AppConstants.MUSIC -> viewLifecycleOwner.lifecycleScope.launch {
                viewModel.music_movies().collectLatest {
                    adapter.submitData(it)
                }
            }

            AppConstants.ROMANCE -> viewLifecycleOwner.lifecycleScope.launch {
                viewModel.romance_movies().collectLatest {
                    adapter.submitData(it)
                }
            }

            AppConstants.CRIME -> viewLifecycleOwner.lifecycleScope.launch {
                viewModel.crime_movies().collectLatest {
                    adapter.submitData(it)
                }
            }

            AppConstants.MYSTERY -> viewLifecycleOwner.lifecycleScope.launch {
                viewModel.mystery_movies().collectLatest {
                    adapter.submitData(it)
                }
            }

            else -> viewLifecycleOwner.lifecycleScope.launch {
                viewModel.horror_movies().collectLatest {
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


    private fun getMovieViewPager(viewpager: ViewPager2) {
        val adapteer = MovieViewPagerAdapter({ Moviepager -> moveMovieGenreToDetail(Moviepager) },
            { Moviepager -> moveMovieGenreToDetail(Moviepager) })
        adapteer.notifyDataSetChanged()
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel1.popular_movies().collectLatest {
                adapteer.submitData(it)
            }
        }
        viewpager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        viewpager.adapter = adapteer

    }

    private fun getMovieViewPagerCurrentItem(viewpager: ViewPager2){

        var total_item: Int? = null
        total_item = 18
        var current_item = 1
        binding.viewpaderNumber.text = "$current_item /$total_item"
        viewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {


            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                current_item = (position + 1) % total_item!!
                if (current_item % 18 == 0) current_item = 18

                binding.viewpaderNumber.text = "$current_item/$total_item"
                viewpager_pos = position
            }

        })

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // handle back button
        requireActivity().onBackPressedDispatcher.addCallback(this) {

            findNavController().navigate(R.id.nav_home_Home)
        }

    }

    private fun moveToMovieGenreDetail(textView: TextView, genre: String) {

        textView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {

                val direction =
                    MovieGenreFragmentDirections.actionNavHomeMovieGenreToNavHomeMovieGenreDeatil(
                        genre
                    )

                findNavController().navigate(direction)
            }

        })
    }
}