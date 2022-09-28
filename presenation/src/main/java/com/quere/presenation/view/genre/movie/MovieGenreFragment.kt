package com.quere.presenation.view.genre.movie


import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.quere.domain.model.common.Detail
import com.quere.presenation.AppConstants
import com.quere.presenation.R
import com.quere.presenation.base.BaseFragment
import com.quere.presenation.view.adapter.GenreAdapter
import com.quere.presenation.view.adapter.HorizontalItemDecorator
import com.quere.presenation.view.adapter.ViewPagerAdapter
import com.quere.presenation.viewmodel.GenreViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieGenreFragment : BaseFragment<com.quere.presenation.databinding.FragmentMovieGenreBinding>(R.layout.fragment_movie_genre) {

    private val genreViewModel: GenreViewModel by activityViewModels()
    private var viewpager_pos = 0

    override fun initView() {

        observeViewModel()

        binding.apply {
            movieStl.visibility = View.GONE

            viewmodel = genreViewModel

            getViewPager(moviePopularViewpager)
            getMovieViewPagerCurrentItem(moviePopularViewpager)

            getGenreRecyclerView(movieAnimationRecyclerview, AppConstants.ANIMATION)
            getGenreRecyclerView(movieFantasyRecyclerview, AppConstants.FANTASY)
            getGenreRecyclerView(movieActionActionRecyclerview, AppConstants.ACTION)
            getGenreRecyclerView(movieMusicRecyclerview, AppConstants.MUSIC)
            getGenreRecyclerView(movieComedyRecyclerview, AppConstants.COMEDY)
            getGenreRecyclerView(movieRomanceRecyclerview, AppConstants.ROMANCE)
            getGenreRecyclerView(movieCrimeRecyclerview, AppConstants.CRIME)
            getGenreRecyclerView(movieMysteryRecyclerview, AppConstants.MYSTERY)
            getGenreRecyclerView(movieHorrorRecyclerview, AppConstants.HORROR)

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
        val viewpagerAdapter : ViewPagerAdapter by lazy {
            ViewPagerAdapter(
                ItemClick = {showDetail(it)}
            )
        }

        viewpager.adapter = viewpagerAdapter
        viewpager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        viewLifecycleOwner.lifecycleScope.launch {
            genreViewModel.getGenrePopular("movie").collectLatest { viewpaerList ->
                viewpagerAdapter.submitData(viewpaerList)
            }
        }

    }
    private fun getMovieViewPagerCurrentItem(viewpager: ViewPager2){

        var total_item: Int? = null
        total_item = 20
        var current_item = 1
        binding.movieViewpaderNumber.text = "$current_item /$total_item"

        viewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {


            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                current_item = (position + 1) % total_item!!
                if (current_item % 20 == 0) current_item = 20

                binding.movieViewpaderNumber.text = "$current_item/$total_item"
                viewpager_pos = position
            }

        })

    }


    private fun showDetail(item: Detail) {
        val GenreToDetail = MovieGenreFragmentDirections.actionMovieGenreFragmentToDetailFragment(
            Detail(
                "movie",
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
        findNavController().navigate(GenreToDetail)
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