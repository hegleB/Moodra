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
import com.quere.moodra.adapter.TVshowGenreAdapter
import com.quere.moodra.databinding.FragmentTvShowBinding
import com.quere.moodra.retrofit.TVshow
import com.quere.moodra.viewmodel.HomeViewModel
import com.quere.moodra.viewmodel.TvViewModel
import com.quere.tvsimpleapp.adapter.TVshowViewPagerAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_movie.*
import kotlinx.android.synthetic.main.fragment_tv_show.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TVshowGenreFragment : Fragment() {

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
            findNavController().navigate(R.id.action_nav_home_TVshowGenre_to_nav_home_MovieGenre)
        })

        binding.home.setOnClickListener({
            findNavController().navigate(R.id.action_nav_home_TVshowGenre_to_nav_home_Home)
        })

        (requireActivity() as AppCompatActivity).supportActionBar?.hide()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            getTVGenreRecycler(tvFantasyRecyclerview, AppConstants.FANTASY)
            getTVGenreRecycler(tvAnimationRecyclerview, AppConstants.ANIMATION)
            getTVGenreRecycler(tvMusicRecyclerview, AppConstants.MUSIC)
            getTVGenreRecycler(tvComedyRecyclerview, AppConstants.COMEDY)
            getTVGenreRecycler(tvRomanceRecyclerview, AppConstants.ROMANCE)
            getTVGenreRecycler(tvCrimeRecyclerview, AppConstants.CRIME)
            getTVGenreRecycler(tvMysteryRecyclerview, AppConstants.MYSTERY)


            getTvViewPager(tvPopularViewpager)
            getTvViewPagerCurrentItem(tvPopularViewpager)

            moveTVGenreToGenreDetail(tvFantasyDetail, "판타지")
            moveTVGenreToGenreDetail(tvAnimationDetail, "애니메이션")
            moveTVGenreToGenreDetail(tvComedyDetail, "코미디")
            moveTVGenreToGenreDetail(tvMusicDetail, "뮤직")
            moveTVGenreToGenreDetail(tvRomanceDetail, "로맨스")
            moveTVGenreToGenreDetail(tvCrimeDetail, "범죄")
            moveTVGenreToGenreDetail(tvMysteryDetail, "미스테리")

        }

        tv_scrollview.setOnScrollChangeListener(object :
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

    private fun moveTVGenreToDetail(tvshow: TVshow) {


        val TVGenreToDetail = TVshowGenreFragmentDirections.actionNavHomeTVshowGenreToNavHomeDetail(

            "tv",
            tvshow.name ?: "이름 엾음",
            tvshow.id,
            tvshow.overview ?: "줄거리 없음",
            false,
            tvshow.poster_path ?: "이미지 없음",
            tvshow.backdrop_path ?: "이미지 없음",
            tvshow.first_air_date ?: "방영날짜 없음",
            tvshow.vote_average ?: "0"
        )
        findNavController().navigate(TVGenreToDetail)


    }

    private fun getTVGenreRecycler(recyclerView: RecyclerView, type: String) {
        val adapter = TVshowGenreAdapter({ TVshow -> moveTVGenreToDetail(TVshow) },
            { TVshow -> moveTVGenreToDetail(TVshow) })


        adapter.addLoadStateListener { loadState ->
            binding.apply {

                if (loadState.refresh is LoadState.Loading) {
                    tvScrollview.visibility = View.GONE
                    tvStl.visibility = View.VISIBLE
                } else {

                    tvStl.visibility = View.GONE
                    tvScrollview.visibility = View.VISIBLE

                }
            }
        }
        when (type) {

            AppConstants.FANTASY -> viewLifecycleOwner.lifecycleScope.launch {
                viewModel.fantasy_tv().collectLatest {
                    adapter.submitData(it)
                }
            }

            AppConstants.ANIMATION -> viewLifecycleOwner.lifecycleScope.launch {
                viewModel.animation_tv().collectLatest {
                    adapter.submitData(it)
                }
            }

            AppConstants.MUSIC -> viewLifecycleOwner.lifecycleScope.launch {
                viewModel.music_tv().collectLatest {
                    adapter.submitData(lifecycle, it)
                }
            }

            AppConstants.COMEDY -> viewLifecycleOwner.lifecycleScope.launch {
                viewModel.comedy_tv().collectLatest {
                    adapter.submitData(lifecycle, it)
                }
            }


            AppConstants.ROMANCE -> viewLifecycleOwner.lifecycleScope.launch {
                viewModel.romance_tv().collectLatest {
                    adapter.submitData(lifecycle, it)
                }
            }

            AppConstants.CRIME -> viewLifecycleOwner.lifecycleScope.launch {
                viewModel.crime_tv().collectLatest {
                    adapter.submitData(lifecycle, it)
                }
            }

            else -> viewLifecycleOwner.lifecycleScope.launch {
                viewModel.mystery_tv().collectLatest {
                    adapter.submitData(lifecycle, it)
                }
            }
        }
        recyclerView.addItemDecoration(HorizontalItemDecorator(10))
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
    }


    private fun getTvViewPager(viewpager: ViewPager2) {

        val adapter = TVshowViewPagerAdapter({ TVshow -> moveTVGenreToDetail(TVshow) },
            { TVshow -> moveTVGenreToDetail(TVshow) })

        viewpager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        viewpager.adapter = adapter
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel1.popular_tv().collectLatest {
                adapter.submitData(it)
            }
        }


    }


    private fun getTvViewPagerCurrentItem(viewpager: ViewPager2){

        total_item = 18
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
                if (current_item % 18 == 0) current_item = 18

                binding.tvViewpaderNumber.text = "$current_item/$total_item"
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

    private fun moveTVGenreToGenreDetail(genre_text_view: TextView, genre: String) {

        genre_text_view.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {

                val direction =
                    TVshowGenreFragmentDirections.actionNavHomeTVshowGenreToNavHomeTVshowGenreDetail(
                        genre
                    )

                findNavController().navigate(direction)
            }

        })
    }
}