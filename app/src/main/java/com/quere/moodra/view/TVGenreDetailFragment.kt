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
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.quere.moodra.R
import com.quere.moodra.adapter.*
import com.quere.moodra.databinding.FragmentTvGenreDetailBinding
import com.quere.moodra.retrofit.TVshow
import com.quere.moodra.viewmodel.TvViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_movie_genre_detail.*
import kotlinx.android.synthetic.main.fragment_search_movie.*
import kotlinx.android.synthetic.main.fragment_search_tv.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.lang.Exception

@AndroidEntryPoint
class TVGenreDetailFragment : Fragment() {

    private lateinit var binding: FragmentTvGenreDetailBinding
    private val viewModel by viewModels<TvViewModel>()
    val twoDepArgs by navArgs<TVGenreDetailFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tv_genre_detail, container, false)
        setHasOptionsMenu(true)

        val adapter = TVGenreDetailAdapter({ tv -> TVDialog(tv) },
            { tv -> TVDialog(tv) })


        binding.apply {
            tvDetaileRecyclerview.setHasFixedSize(true)
            tvDetaileRecyclerview.layoutManager = StaggeredGridLayoutManager(3,
                StaggeredGridLayoutManager.VERTICAL)
            tvDetaileRecyclerview.itemAnimator = null
            tvDetaileRecyclerview.adapter = adapter

            tvDetailToolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_24)
            tvDetailToolbar.setNavigationOnClickListener(object : View.OnClickListener{
                override fun onClick(p0: View?) {
                    findNavController().navigateUp()
                }

            })

            tvDetailText.setText(twoDepArgs.genre)


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

                viewModel.tv_detail(twoDepArgs.genre).collectLatest {

                    adapter.submitData(viewLifecycleOwner.lifecycle,it)
                    adapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
                }
            }
        } catch (e: Exception){

        }



        return binding.root
    }

    private fun TVDialog(tv: TVshow) {

        val direction = TVGenreDetailFragmentDirections.actionTVGenreDetailFragmentToDetailFragment(
            "tv",
            tv.name ?: "제목 없음",
            tv.id,
            tv.overview ?: "줄거리 없음",
            false,
            tv.poster_path ?: "이미지 없음",
            tv.backdrop_path ?: "이미지 없음",
            tv.first_air_date ?: "개봉날짜 없음",
            tv.vote_average?: "0"
        )
        findNavController().navigate(direction)


    }



}