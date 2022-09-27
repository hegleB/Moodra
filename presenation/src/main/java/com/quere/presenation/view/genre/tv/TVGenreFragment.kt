package com.quere.presenation.view.genre.tv

import android.os.Bundle
import androidx.fragment.app.Fragment
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
import com.quere.presenation.databinding.FragmentTVGenreBinding
import com.quere.presenation.view.adapter.GenreAdapter
import com.quere.presenation.view.adapter.HorizontalItemDecorator
import com.quere.presenation.view.adapter.ViewPagerAdapter
import com.quere.presenation.viewmodel.GenreViewModel

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TVGenreFragment : BaseFragment<FragmentTVGenreBinding>(R.layout.fragment_t_v_genre) {

    private val genreViewModel: GenreViewModel by activityViewModels()
    private var viewpager_pos = 0
    override fun initView() {
        binding.apply {
            tvStl.visibility = View.GONE

            home.setOnClickListener { TVGenreToHome() }
            moviesTv.setOnClickListener { TVGenreToMovieGenre() }

            getViewPager(tvPopularViewpager)
            getTVViewPagerCurrentItem(tvPopularViewpager)

            getGenreRecyclerView(tvAnimationRecyclerview, AppConstants.ANIMATION)
            getGenreRecyclerView(tvFantasyRecyclerview, AppConstants.FANTASY)
            getGenreRecyclerView(tvMusicRecyclerview, AppConstants.MUSIC)
            getGenreRecyclerView(tvComedyRecyclerview, AppConstants.COMEDY)
            getGenreRecyclerView(tvRomanceRecyclerview, AppConstants.ROMANCE)
            getGenreRecyclerView(tvCrimeRecyclerview, AppConstants.CRIME)
            getGenreRecyclerView(tvMysteryRecyclerview, AppConstants.MYSTERY)


        }
    }

    private fun getGenreRecyclerView(recyclerView: RecyclerView, genre: String) {
        val genreAdapter: GenreAdapter by lazy {
            GenreAdapter(
                ItemClick = { doOnClick(it) }
            )
        }
        recyclerView.adapter = genreAdapter
        recyclerView.addItemDecoration(HorizontalItemDecorator(10))
        collectFlow("tv",genre, genreAdapter)

    }

    private fun collectFlow(type: String, genre: String,genreAdapter: GenreAdapter) {

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
                ItemClick = {doOnClick(it)}
            )
        }

        viewpager.adapter = viewpagerAdapter
        viewpager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        viewLifecycleOwner.lifecycleScope.launch {
            genreViewModel.getGenrePopular("tv").collectLatest { viewpaerList ->
                viewpagerAdapter.submitData(viewpaerList)
            }
        }

    }
    private fun getTVViewPagerCurrentItem(viewpager: ViewPager2){

        var total_item: Int? = null
        total_item = 20
        var current_item = 1
        binding.tvViewpaderNumber.text = "$current_item /$total_item"

        viewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {


            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                current_item = (position + 1) % total_item!!
                if (current_item % 20 == 0) current_item = 20

                binding.tvViewpaderNumber.text = "$current_item/$total_item"
                viewpager_pos = position
            }

        })

    }



    private fun doOnClick(item: Detail) {
        val GenreToDetail = TVGenreFragmentDirections.actionTVGenreFragmentToDetailFragment(
            Detail(
                false,
                    "",
                item.name,
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

    private fun TVGenreToMovieGenre(){
        val tvGenre = TVGenreFragmentDirections.actionTVGenreFragmentToMovieGenreFragment()

        findNavController().navigate(tvGenre)
    }

    private fun TVGenreToHome(){
        val tvGenre = TVGenreFragmentDirections.actionTVGenreFragmentToHomeFragment()

        findNavController().navigate(tvGenre)
    }

}