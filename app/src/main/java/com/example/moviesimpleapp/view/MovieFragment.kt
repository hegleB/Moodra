package com.example.moviesimpleapp.view


import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.moviesimpleapp.AppConstants
import com.example.moviesimpleapp.R
import com.example.moviesimpleapp.adapter.HorizontalItemDecorator
import com.example.moviesimpleapp.adapter.MovieGenreAdatper
import com.example.moviesimpleapp.adapter.MovieViewPagerAdapter
import com.example.moviesimpleapp.databinding.FragmentMovieBinding
import com.example.moviesimpleapp.retrofit.Movie
import com.example.moviesimpleapp.viewmodel.HomeViewModel
import com.example.moviesimpleapp.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_movie.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieFragment : Fragment() {


    private lateinit var binding: FragmentMovieBinding
    private val viewModel by viewModels<MovieViewModel>()
    private val viewModel1 by viewModels<HomeViewModel>()
    private var viewpager_pos = 0
    var total_item: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie, container, false)

        binding.tvShowTv.setOnClickListener({
            findNavController().navigate(R.id.action_movieFragment_to_TVshowFragment)
        })

        binding.home.setOnClickListener({
            findNavController().navigate(R.id.action_movieFragment_to_homeFragment)
        })

        (requireActivity() as AppCompatActivity).supportActionBar?.hide()

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.apply {
            MovieGenreRecycler(movieActionActionRecyclerview,AppConstants.ACTION)
            MovieGenreRecycler(movieFantasyRecyclerview,AppConstants.FANTASY)
            MovieGenreRecycler(movieAnimationRecyclerview,AppConstants.ANIMATION)
            MovieGenreRecycler(movieComedyRecyclerview,AppConstants.COMEDY)
            MovieGenreRecycler(movieMusicRecyclerview,AppConstants.MUSIC)
            MovieGenreRecycler(movieRomanceRecyclerview,AppConstants.ROMANCE)
            MovieGenreRecycler(movieCrimeRecyclerview,AppConstants.CRIME)
            MovieGenreRecycler(movieMysteryRecyclerview,AppConstants.MYSTERY)
            MovieGenreRecycler(movieHorrorRecyclerview,AppConstants.HORROR)

            MovieViewPager(moviePopularViewpager)

            DetailClick(movie_action_detail,"액션")
            DetailClick(movie_fantasy_detail,"판타지")
            DetailClick(movie_animation_detail,"애니메이션")
            DetailClick(movie_comedy_detail,"코미디")
            DetailClick(movie_music_detail,"뮤직")
            DetailClick(movie_romance_detail,"로맨스")
            DetailClick(movie_crime_detail,"범죄")
            DetailClick(movie_mystery_detail,"미스테리")
            DetailClick(movie_horror_detail,"호러")
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



    private fun MovieDialog(movie: Movie) {

        val direction = MovieFragmentDirections.actionMovieFragmentToDetailFragment(
            "movie",
            movie.title ?: "제목 없음",
            movie.id,
            movie.overview ?: "줄거리 없음",
            false,
            movie.poster_path ?: "이미지 없음",
            movie.backdrop_path ?: "이미지 없음",
            movie.release_date ?: "개봉날짜 없음",
            movie.vote_average?: "0",
        )
        findNavController().navigate(direction)


    }

    private fun MovieGenreRecycler(recyclerView: RecyclerView, type: String) {
        val adapter = MovieGenreAdatper({ Movie -> MovieDialog(Movie) },
            { Movie -> MovieDialog(Movie) })
        adapter.notifyDataSetChanged()
        when(type){
            AppConstants.ACTION -> viewLifecycleOwner.lifecycleScope.launch{ viewModel.action_movies().collectLatest{
                adapter.submitData(lifecycle,it)}
            }

            AppConstants.FANTASY -> viewLifecycleOwner.lifecycleScope.launch{viewModel.fantasy_movies().collectLatest{
                adapter.submitData(it)}
            }

            AppConstants.ANIMATION -> viewLifecycleOwner.lifecycleScope.launch{viewModel.animation_movies().collectLatest{
                adapter.submitData(it)}
            }

            AppConstants.COMEDY -> viewLifecycleOwner.lifecycleScope.launch{viewModel.comedy_movies().collectLatest{
                adapter.submitData(it)}
            }

            AppConstants.MUSIC -> viewLifecycleOwner.lifecycleScope.launch{viewModel.music_movies().collectLatest{
                adapter.submitData(it)}
            }

            AppConstants.ROMANCE -> viewLifecycleOwner.lifecycleScope.launch{viewModel.romance_movies().collectLatest{
                adapter.submitData(it)}
            }

            AppConstants.CRIME -> viewLifecycleOwner.lifecycleScope.launch{viewModel.crime_movies().collectLatest{
                adapter.submitData(it)}
            }

            AppConstants.MYSTERY -> viewLifecycleOwner.lifecycleScope.launch{viewModel.mystery_movies().collectLatest{
                adapter.submitData(it)}
            }

            else ->  viewLifecycleOwner.lifecycleScope.launch{viewModel.horror_movies().collectLatest {
                adapter.submitData(it)}
            }
        }
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(HorizontalItemDecorator(10))
        recyclerView.setHasFixedSize(true)
    }

    private fun MovieViewPager(viewpager: ViewPager2) {
        val adapteer = MovieViewPagerAdapter({ Moviepager -> MovieDialog(Moviepager) },
            { Moviepager -> MovieDialog(Moviepager) })
        adapteer.notifyDataSetChanged()
        viewLifecycleOwner.lifecycleScope.launch{viewModel1.popular_movies().collectLatest{
            adapteer.submitData(it)}
        }
        viewpager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        viewpager.adapter = adapteer


        total_item = 20
        var current_item=1
        binding.viewpaderNumber.text = "$current_item /$total_item"
        viewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {


            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                current_item = (position + 1) % total_item!!
                if(current_item%20==0) current_item=20

                binding.viewpaderNumber.text = "$current_item/$total_item"
                viewpager_pos=position
            }

        })

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // handle back button
        requireActivity().onBackPressedDispatcher.addCallback(this) {

            findNavController().navigate(R.id.homeFragment2)
        }

    }

    private fun DetailClick(genre_text_view : TextView, genre : String ){

        genre_text_view.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {

                val direction = MovieFragmentDirections.actionMovieFragmentToMovieGenreDetailFragment(genre)

                findNavController().navigate(direction)
            }

        })
    }
}