package com.quere.presenation.view.genre.movie


import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.quere.domain.model.common.Detail
import com.quere.presenation.AppConstants
import com.quere.presenation.R
import com.quere.presenation.base.BaseFragment
import com.quere.presenation.databinding.FragmentMovieGenreBinding
import com.quere.presenation.view.adapter.GenreAdapter
import com.quere.presenation.view.adapter.HorizontalItemDecorator
import com.quere.presenation.view.adapter.MovieViewPagerAdapter
import com.quere.presenation.viewmodel.DetailViewModel
import com.quere.presenation.viewmodel.GenreViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieGenreFragment : BaseFragment<FragmentMovieGenreBinding>(R.layout.fragment_movie_genre) {

    private val genreViewModel: GenreViewModel by activityViewModels()
    private val detailViewModel: DetailViewModel by activityViewModels()

    override fun initView() {

        observeViewModel()

        binding.apply {
            stilMovieGenre.visibility = View.GONE

            viewmodel = genreViewModel
            getViewPager(viewPagerMovieGenrePopular)

            getGenreRecyclerView(recyclerViewMovieGenreAnimation, AppConstants.ANIMATION)
            getGenreRecyclerView(recyclerViewMovieGenreFantasy, AppConstants.FANTASY)
            getGenreRecyclerView(recyclerViewMovieGenreAction, AppConstants.ACTION)
            getGenreRecyclerView(recyclerViewMovieGenreMusic, AppConstants.MUSIC)
            getGenreRecyclerView(recyclerViewMovieGenreComedy, AppConstants.COMEDY)
            getGenreRecyclerView(recyclerViewMovieGenreRomance, AppConstants.ROMANCE)
            getGenreRecyclerView(recyclerViewMovieGenreCrime, AppConstants.CRIME)
            getGenreRecyclerView(recyclerViewMovieGenreMystery, AppConstants.MYSTERY)
            getGenreRecyclerView(recyclerViewMovieGenreHorror, AppConstants.HORROR)

        }
    }

    private fun observeViewModel() {

        genreViewModel.fragmentHome.observe(viewLifecycleOwner) {
            if (it.consumed) return@observe
            showHome()
            it.consume()
        }

        genreViewModel.fragmentTv.observe(viewLifecycleOwner) {
            if (it.consumed) return@observe
            showTV()
            it.consume()
        }

        genreViewModel.animation.observe(viewLifecycleOwner) {
            if (it.consumed) return@observe
            showMovieGenreAll("애니메이션")
            it.consume()
        }

        genreViewModel.fantasy.observe(viewLifecycleOwner) {
            if (it.consumed) return@observe
            showMovieGenreAll("판타지")
            it.consume()
        }

        genreViewModel.action.observe(viewLifecycleOwner) {
            if (it.consumed) return@observe
            showMovieGenreAll("액션")
            it.consume()
        }

        genreViewModel.music.observe(viewLifecycleOwner) {
            if (it.consumed) return@observe
            showMovieGenreAll("뮤직")
            it.consume()
        }

        genreViewModel.comedy.observe(viewLifecycleOwner) {
            if (it.consumed) return@observe
            showMovieGenreAll("코미디")
            it.consume()
        }

        genreViewModel.romance.observe(viewLifecycleOwner) {
            if (it.consumed) return@observe
            showMovieGenreAll("로맨스")
            it.consume()
        }

        genreViewModel.crime.observe(viewLifecycleOwner) {
            if (it.consumed) return@observe
            showMovieGenreAll("범죄")
            it.consume()
        }

        genreViewModel.mystery.observe(viewLifecycleOwner) {
            if (it.consumed) return@observe
            showMovieGenreAll("미스테리")
            it.consume()
        }

        genreViewModel.horror.observe(viewLifecycleOwner) {
            if (it.consumed) return@observe
            showMovieGenreAll("호러")
            it.consume()
        }

    }

    private fun getGenreRecyclerView(recyclerView: RecyclerView, genre: String) {
        val genreAdapter: GenreAdapter by lazy {
            GenreAdapter(
                ItemClick = { showDetail(it) }
            )
        }
        recyclerView.adapter = genreAdapter
        recyclerView.addItemDecoration(HorizontalItemDecorator(10))
        collectFlow("movie",genre, genreAdapter)
    }

    private fun showMovieGenreAll(genre: String) {
        val genreAll = MovieGenreFragmentDirections.actionMovieGenreFragmentToGenreAllFragment(
            "movie",genre
        )

        findNavController().navigate(genreAll)
    }

    private fun collectFlow(type: String, genre: String,genreAdapter: GenreAdapter ) {

        when (genre) {
            AppConstants.ANIMATION -> {
                viewLifecycleOwner.lifecycleScope.launch{
                    genreViewModel.getGenre(type,genre)!!.collectLatest { genreList ->

                        genreAdapter.submitData(genreList)
                    }
                }
            }
            AppConstants.ACTION -> {
                viewLifecycleOwner.lifecycleScope.launch{
                    genreViewModel.getGenre(type,genre)!!.collectLatest { genreList ->

                        genreAdapter.submitData(genreList)
                    }
                }
            }
            AppConstants.FANTASY -> {
                viewLifecycleOwner.lifecycleScope.launch{
                    genreViewModel.getGenre(type,genre)!!.collectLatest { genreList ->

                        genreAdapter.submitData(genreList)
                    }
                }
            }
            AppConstants.MUSIC -> {
                viewLifecycleOwner.lifecycleScope.launch{
                    genreViewModel.getGenre(type,genre)!!.collectLatest { genreList ->

                        genreAdapter.submitData(genreList)
                    }
                }
            }
            AppConstants.COMEDY -> {
                viewLifecycleOwner.lifecycleScope.launch{
                    genreViewModel.getGenre(type,genre)!!.collectLatest { genreList ->
                        genreAdapter.submitData(genreList)
                    }
                }
            }
            AppConstants.ROMANCE -> {
                viewLifecycleOwner.lifecycleScope.launch{
                    genreViewModel.getGenre(type,genre)!!.collectLatest { genreList ->
                        genreAdapter.submitData(genreList)
                    }
                }
            }
            AppConstants.CRIME -> {
                viewLifecycleOwner.lifecycleScope.launch{
                    genreViewModel.getGenre(type,genre)!!.collectLatest { genreList ->
                        genreAdapter.submitData(genreList)
                    }
                }
            }
            AppConstants.MYSTERY -> {
                viewLifecycleOwner.lifecycleScope.launch{
                    genreViewModel.getGenre(type,genre)!!.collectLatest { genreList ->

                        genreAdapter.submitData(genreList)
                    }
                }
            }
            AppConstants.HORROR -> {
                viewLifecycleOwner.lifecycleScope.launch{
                    genreViewModel.getGenre(type,genre)!!.collectLatest { genreList ->
                        genreAdapter.submitData(genreList)
                    }
                }
            }
        }

    }

    private fun getViewPager(viewpager: ViewPager2){
        val viewpagerAdapter : MovieViewPagerAdapter by lazy {
            MovieViewPagerAdapter(
                ItemClick = {showDetail(it)}
            )
        }

        viewpager.adapter = viewpagerAdapter
        viewpager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        viewpager.setCurrentItem(0,true)

        viewLifecycleOwner.lifecycleScope.launch {
            genreViewModel.getMoviePopular().collectLatest { viewpaerList ->
                viewpagerAdapter.submitData(viewpaerList)
            }
        }
        getMovieViewPagerCurrentItem(binding.viewPagerMovieGenrePopular)

    }

    private fun getMovieViewPagerCurrentItem(viewpager: ViewPager2){

        viewpager.setCurrentItem(0,true)

        lifecycleScope.launch {
            delay(100)

            genreViewModel.pageMovieViewPager.observe(viewLifecycleOwner) {
                viewpager.setCurrentItem(it, true)

            }
        }
        viewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                if (positionOffsetPixels == 0) {
                    viewpager.setCurrentItem(position)
                }
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position!=0) {
                    genreViewModel.setPositionMovieViewPager(position % 20)
                }
            }
        })


    }





    private fun showDetail(item: Detail) {

        viewLifecycleOwner.lifecycleScope.launch {
            val movie = detailViewModel.getMovieDetail(item.id?:0)

            val GenreToDetail =
                MovieGenreFragmentDirections.actionMovieGenreFragmentToDetailFragment(
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
            findNavController().navigate(GenreToDetail)
        }
    }

    private fun showTV(){
        val moveGenre = MovieGenreFragmentDirections.actionMovieGenreFragmentToTVGenreFragment()

        findNavController().navigate(moveGenre)
    }

    private fun showHome(){
        val moveGenre = MovieGenreFragmentDirections.actionMovieGenreFragmentToHomeFragment()

        findNavController().navigate(moveGenre)
    }


}