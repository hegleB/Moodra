package com.example.moviesimpleapp.view

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
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.moviesimpleapp.AppConstants
import com.example.moviesimpleapp.R
import com.example.moviesimpleapp.adapter.HorizontalItemDecorator
import com.example.moviesimpleapp.adapter.TVshowGenreAdapter
import com.example.moviesimpleapp.databinding.FragmentTvShowBinding
import com.example.moviesimpleapp.retrofit.TVshow
import com.example.moviesimpleapp.viewmodel.HomeViewModel
import com.example.moviesimpleapp.viewmodel.MovieViewModel
import com.example.moviesimpleapp.viewmodel.TvViewModel
import com.example.tvsimpleapp.adapter.TVshowViewPagerAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_movie.*
import kotlinx.android.synthetic.main.fragment_tv_show.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TVshowFragment : Fragment(){

    private lateinit var binding: FragmentTvShowBinding
    private val viewModel by viewModels<TvViewModel>()
    private val viewModel1 by viewModels<HomeViewModel>()


    var total_item: Int? = null

    private var viewpager_pos: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate<FragmentTvShowBinding>(
            inflater,
            R.layout.fragment_tv_show,
            container,
            false
        )
        binding.moviesTv.setOnClickListener({
            findNavController().navigate(R.id.action_TVshowFragment_to_movieFragment)
        })

        binding.home.setOnClickListener({
            findNavController().navigate(R.id.action_TVshowFragment_to_homeFragment)
        })

        (requireActivity() as AppCompatActivity).supportActionBar?.hide()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            TVGenreRecycler(tvFantasyRecyclerview,AppConstants.FANTASY)
            TVGenreRecycler(tvAnimationRecyclerview,AppConstants.ANIMATION)
            TVGenreRecycler(tvMusicRecyclerview,AppConstants.MUSIC)
            TVGenreRecycler(tvComedyRecyclerview,AppConstants.COMEDY)
            TVGenreRecycler(tvRomanceRecyclerview,AppConstants.ROMANCE)
            TVGenreRecycler(tvCrimeRecyclerview,AppConstants.CRIME)
            TVGenreRecycler(tvMysteryRecyclerview,AppConstants.MYSTERY)


            TVViewPager(tvPopularViewpager)

            DetailClick(tvFantasyDetail,"판타지")
            DetailClick(tvAnimationDetail,"애니메이션")
            DetailClick(tvComedyDetail,"코미디")
            DetailClick(tvMusicDetail,"뮤직")
            DetailClick(tvRomanceDetail,"로맨스")
            DetailClick(tvCrimeDetail,"범죄")
            DetailClick(tvMysteryDetail,"미스테리")

        }

        tv_move_scrollview.setOnScrollChangeListener(object :
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
                        tv_actionbar.setBackgroundColor(Color.BLACK)
                        tv_actionbar.getBackground()
                            .setAlpha((scrollY.toFloat() / 1300.toFloat() * 255.toFloat()).toInt())
                    }
                }
            }

        })

    }

    private fun TVDialog(tvshow: TVshow) {


        val direction = TVshowFragmentDirections.actionTVshowFragmentToDetailFragment(

            "tv",
            tvshow.name ?: "이름 엾음",
            tvshow.id,
            tvshow.overview ?: "줄거리 없음",
            false,
            tvshow.poster_path ?: "이미지 없음",
            tvshow.backdrop_path ?: "이미지 없음",
            tvshow.first_air_date ?: "방영날짜 없음",
            tvshow.vote_average?: "0"
            )
        findNavController().navigate(direction)


    }

    private fun TVGenreRecycler(recyclerView: RecyclerView, type: String) {
        val adapter = TVshowGenreAdapter({ TVshow -> TVDialog(TVshow) },
            { TVshow -> TVDialog(TVshow) })
        adapter.notifyDataSetChanged()
        when(type){

            AppConstants.FANTASY -> viewLifecycleOwner.lifecycleScope.launch{viewModel.fantasy_tv().collectLatest{
                adapter.submitData(it)}
            }

            AppConstants.ANIMATION -> viewLifecycleOwner.lifecycleScope.launch{viewModel.animation_tv().collectLatest{
                adapter.submitData(it)}
            }

            AppConstants.MUSIC -> viewLifecycleOwner.lifecycleScope.launch{viewModel.music_tv().collectLatest{
                adapter.submitData(lifecycle,it)}
            }

            AppConstants.COMEDY -> viewLifecycleOwner.lifecycleScope.launch{viewModel.comedy_tv().collectLatest{
                adapter.submitData(lifecycle,it)}
            }


            AppConstants.ROMANCE -> viewLifecycleOwner.lifecycleScope.launch{viewModel.romance_tv().collectLatest{
                adapter.submitData(lifecycle,it)}
            }

            AppConstants.CRIME -> viewLifecycleOwner.lifecycleScope.launch{viewModel.crime_tv().collectLatest{
                adapter.submitData(lifecycle,it)}
            }

            else ->  viewLifecycleOwner.lifecycleScope.launch{viewModel.mystery_tv().collectLatest {
                adapter.submitData(lifecycle, it)}
            }
        }
        recyclerView.addItemDecoration(HorizontalItemDecorator(10))
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
    }

    private fun TVViewPager(viewpager: ViewPager2) {

        val adapter = TVshowViewPagerAdapter({ TVshow -> TVDialog(TVshow) },
            { TVshow -> TVDialog(TVshow) })
        adapter.notifyDataSetChanged()

        viewpager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        viewpager.adapter = adapter
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel1.popular_tv().collectLatest {
                adapter.submitData(it)
            }
        }

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
                if(current_item%20==0) current_item=20

                binding.tvViewpaderNumber.text = "$current_item/$total_item"
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

                val direction = TVshowFragmentDirections.actionTVshowFragmentToTVGenreDetailFragment(genre)

                findNavController().navigate(direction)
            }

        })
    }
}