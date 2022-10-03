package com.quere.presenation.view.genre.tv

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
import com.quere.presenation.databinding.FragmentTVGenreBinding
import com.quere.presenation.view.adapter.GenreAdapter
import com.quere.presenation.view.adapter.HorizontalItemDecorator
import com.quere.presenation.view.adapter.TVViewPagerAdapter
import com.quere.presenation.viewmodel.DetailViewModel
import com.quere.presenation.viewmodel.GenreViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TVGenreFragment : BaseFragment<FragmentTVGenreBinding>(R.layout.fragment_t_v_genre) {

    private val genreViewModel: GenreViewModel by activityViewModels()
    private val detailViewModel: DetailViewModel by activityViewModels()

    override fun initView() {

        observeViewModel()

        binding.apply {
            stlTvGenre.visibility = View.GONE

            viewmodel = genreViewModel
            getViewPager(viewPagerTvGenrePopular)
            getTVViewPagerCurrentItem(viewPagerTvGenrePopular)

            getGenreRecyclerView(recyclerViewTvGenreAnimation, AppConstants.ANIMATION)
            getGenreRecyclerView(recyclerViewTvGenreFantasy, AppConstants.FANTASY)
            getGenreRecyclerView(recyclerViewTvGenreMusic, AppConstants.MUSIC)
            getGenreRecyclerView(recyclerViewTvGenreComedy, AppConstants.COMEDY)
            getGenreRecyclerView(recyclerViewTvGenreRomance, AppConstants.ROMANCE)
            getGenreRecyclerView(recyclerViewTvGenreCrime, AppConstants.CRIME)
            getGenreRecyclerView(recyclerViewTvGenreMystery, AppConstants.MYSTERY)


        }
    }

    private fun observeViewModel() {

        genreViewModel.fragmentHome.observe(viewLifecycleOwner) {
            if (it.consumed) return@observe
            showHome()
            it.consume()
        }

        genreViewModel.fragmentMovie.observe(viewLifecycleOwner) {
            if (it.consumed) return@observe
            showMovie()
            it.consume()
        }

        genreViewModel.animation.observe(viewLifecycleOwner) {
            if (it.consumed) return@observe
            showMovieGenreAll("애니메이션",AppConstants.ANIMATION)
            it.consume()
        }

        genreViewModel.fantasy.observe(viewLifecycleOwner) {
            if (it.consumed) return@observe
            showMovieGenreAll("판타지",AppConstants.FANTASY)
            it.consume()
        }

        genreViewModel.music.observe(viewLifecycleOwner) {
            if (it.consumed) return@observe
            showMovieGenreAll("뮤직",AppConstants.MUSIC)
            it.consume()
        }

        genreViewModel.comedy.observe(viewLifecycleOwner) {
            if (it.consumed) return@observe
            showMovieGenreAll("코미디",AppConstants.COMEDY)
            it.consume()
        }

        genreViewModel.romance.observe(viewLifecycleOwner) {
            if (it.consumed) return@observe
            showMovieGenreAll("로맨스",AppConstants.ROMANCE)
            it.consume()
        }

        genreViewModel.crime.observe(viewLifecycleOwner) {
            if (it.consumed) return@observe
            showMovieGenreAll("범죄",AppConstants.CRIME)
            it.consume()
        }

        genreViewModel.mystery.observe(viewLifecycleOwner) {
            if (it.consumed) return@observe
            showMovieGenreAll("미스테리",AppConstants.MYSTERY)
            it.consume()
        }

    }

    private fun showMovieGenreAll(genre: String, genreId: String) {
        val genreAll = TVGenreFragmentDirections.actionTVGenreFragmentToGenreAllFragment(
            "tv",genre,genreId
        )

        findNavController().navigate(genreAll)
    }

    private fun getGenreRecyclerView(recyclerView: RecyclerView, genre: String) {
        val genreAdapter: GenreAdapter by lazy {
            GenreAdapter(
                ItemClick = { doOnClick(it) }
            )
        }
        recyclerView.adapter = genreAdapter
        recyclerView.addItemDecoration(HorizontalItemDecorator(10))
        collectFlow("tv", genre, genreAdapter)

    }

    private fun collectFlow(type: String, genre: String, genreAdapter: GenreAdapter) {

        when (genre) {
            AppConstants.ANIMATION -> {
                viewLifecycleOwner.lifecycleScope.launch {
                    genreViewModel.getGenre(type, genre)!!.collectLatest { genreList ->

                        genreAdapter.submitData(genreList)
                    }
                }
            }
            AppConstants.ACTION -> {
                viewLifecycleOwner.lifecycleScope.launch {
                    genreViewModel.getGenre(type, genre)!!.collectLatest { genreList ->

                        genreAdapter.submitData(genreList)
                    }
                }
            }
            AppConstants.FANTASY -> {
                viewLifecycleOwner.lifecycleScope.launch {
                    genreViewModel.getGenre(type, genre)!!.collectLatest { genreList ->
                        genreAdapter.submitData(genreList)
                    }
                }
            }
            AppConstants.MUSIC -> {
                viewLifecycleOwner.lifecycleScope.launch {
                    genreViewModel.getGenre(type, genre)!!.collectLatest { genreList ->

                        genreAdapter.submitData(genreList)
                    }
                }
            }
            AppConstants.COMEDY -> {
                viewLifecycleOwner.lifecycleScope.launch {
                    genreViewModel.getGenre(type, genre)!!.collectLatest { genreList ->

                        genreAdapter.submitData(genreList)
                    }
                }
            }
            AppConstants.ROMANCE -> {
                viewLifecycleOwner.lifecycleScope.launch {
                    genreViewModel.getGenre(type, genre)!!.collectLatest { genreList ->

                        genreAdapter.submitData(genreList)
                    }
                }
            }
            AppConstants.CRIME -> {
                viewLifecycleOwner.lifecycleScope.launch {
                    genreViewModel.getGenre(type, genre)!!.collectLatest { genreList ->

                        genreAdapter.submitData(genreList)
                    }
                }
            }
            AppConstants.MYSTERY -> {
                viewLifecycleOwner.lifecycleScope.launch {
                    genreViewModel.getGenre(type, genre)!!.collectLatest { genreList ->

                        genreAdapter.submitData(genreList)
                    }
                }
            }
            AppConstants.HORROR -> {
                viewLifecycleOwner.lifecycleScope.launch {
                    genreViewModel.getGenre(type, genre)!!.collectLatest { genreList ->

                        genreAdapter.submitData(genreList)
                    }
                }
            }
        }

    }

    private fun getViewPager(viewpager: ViewPager2) {
        val viewpagerAdapter: TVViewPagerAdapter by lazy {
            TVViewPagerAdapter(
                ItemClick = { doOnClick(it) }
            )
        }

        viewpager.adapter = viewpagerAdapter
        viewpager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        viewLifecycleOwner.lifecycleScope.launch {
            genreViewModel.getTVPopular().collectLatest { viewpaerList ->
                viewpagerAdapter.submitData(viewpaerList)
            }
        }



    }

    private fun getTVViewPagerCurrentItem(viewpager: ViewPager2) {

        viewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                genreViewModel.setPositionTVViewPager(position % 20)
            }
        })


    }


    private fun doOnClick(item: Detail) {
        viewLifecycleOwner.lifecycleScope.launch {
            val tv = detailViewModel.getTVDetail(item.id?:0)
            val GenreToDetail = TVGenreFragmentDirections.actionTVGenreFragmentToDetailFragment(
                Detail(
                    "tv",
                    "",
                    tv.name,
                    tv.genres,
                    tv.id,
                    tv.overview,
                    false,
                    tv.poster_path,
                    tv.backdrop_path,
                    tv.first_air_date,
                    tv.episode_run_time.sum(),
                    tv.video,
                    tv.vote_average
                )
            )
            findNavController().navigate(GenreToDetail)
        }
    }

    private fun showMovie() {
        val tvGenre = TVGenreFragmentDirections.actionTVGenreFragmentToMovieGenreFragment()
        findNavController().navigate(tvGenre)
    }

    private fun showHome() {
        val tvGenre = TVGenreFragmentDirections.actionTVGenreFragmentToHomeFragment()

        findNavController().navigate(tvGenre)
    }

}