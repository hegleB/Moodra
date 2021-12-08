package com.quere.moodra.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.quere.moodra.R
import com.quere.moodra.adapter.MovieSearchDetailAdapter
import com.quere.moodra.adapter.TVSearchDetailAdapter
import com.quere.moodra.databinding.FragmentSearchDetailBinding

import com.quere.moodra.retrofit.MovieSearch
import com.quere.moodra.retrofit.TVshowSearch
import com.quere.moodra.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_search_movie.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.lang.Exception

@AndroidEntryPoint
class SearchDetaileFragment : Fragment() {


    private lateinit var binding: FragmentSearchDetailBinding
    private val viewModel by viewModels<SearchViewModel>()

    val twoDepArgs by navArgs<SearchDetaileFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(requireContext()),
            R.layout.fragment_search_detail,
            container,
            false
        )

        when(twoDepArgs.type){
            "movie" -> {
                val adapter = MovieSearchDetailAdapter({ movies -> MovieSearchDialog(movies) },
                    { movies -> MovieSearchDialog(movies) })


                binding.apply {

                    searchDetaileRecyclerview.layoutManager = GridLayoutManager(context,2)
                    searchDetaileRecyclerview.adapter = adapter


                }

                adapter.addLoadStateListener { loadState ->
                    binding.apply {

                        // empty view
                        if (loadState.source.refresh is LoadState.NotLoading &&
                            loadState.append.endOfPaginationReached &&
                            adapter.itemCount < 1
                        ) {
                            movie_search_recyclerView.isVisible = false
                        } else {

                        }
                    }
                }


                try {
                    viewLifecycleOwner.lifecycleScope.launch {
                        viewModel.getMoviesDetail(twoDepArgs.query).collectLatest {

                            adapter.submitData(viewLifecycleOwner.lifecycle,it)
                            adapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
                        }
                    }
                } catch (e:Exception){

                }
            }

            "tv" -> {

                val adapter = TVSearchDetailAdapter({ tvs -> TVSearchDialog(tvs) },
                    { tvs -> TVSearchDialog(tvs) })

                binding.apply {

                    searchDetaileRecyclerview.layoutManager = GridLayoutManager(context,2)

                    searchDetaileRecyclerview.adapter = adapter



                }

                adapter.addLoadStateListener { loadState ->
                    binding.apply {

                        // empty view
                        if (loadState.source.refresh is LoadState.NotLoading &&
                            loadState.append.endOfPaginationReached &&
                            adapter.itemCount < 1
                        ) {
                            movie_search_recyclerView.isVisible = false
                        } else {

                        }
                    }
                }


                try {
                    viewLifecycleOwner.lifecycleScope.launch {
                        viewModel.getTvshowDetail(twoDepArgs.query).collectLatest {
                            adapter.submitData(viewLifecycleOwner.lifecycle,it)
                            adapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
                        }
                    }
                } catch (e:Exception){

                }

            }
        }

        binding.apply {
            searchDetailToolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_24)

            searchDetailToolbar.setNavigationOnClickListener(object : View.OnClickListener{
                override fun onClick(p0: View?) {
                    findNavController().navigateUp()
                }

            })

            searchDetailText.setText(twoDepArgs.query)



        }




        return binding.root
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {

        when(item.itemId){

        }
        return super.onContextItemSelected(item)
    }

    private fun MovieSearchDialog(movie_search: MovieSearch) {

        val direction = SearchDetaileFragmentDirections.actionSearchDetaileFragmentToDetailFragment(
            "movie",
            movie_search.title ?: "제목 없음",
            movie_search.id!!,
            movie_search.overview ?: "줄거리 없음",
            movie_search.adult ?: false,
            movie_search.poster_path ?: "이미지 없음",
            movie_search.backdrop_path ?: "이미지 없음",
            movie_search.release_date ?: "개봉날짜 없음",
            movie_search.vote_average?: "0"
        )

        findNavController().navigate(direction)


    }

    private fun TVSearchDialog(tv_search: TVshowSearch) {

        val direction = SearchDetaileFragmentDirections.actionSearchDetaileFragmentToDetailFragment(
            "tv",
            tv_search.name ?: "제목 없음",
            tv_search.id!!,
            tv_search.overview ?: "줄거리 없음",
            false,
            tv_search.poster_path ?: "이미지 없음",
            tv_search.backdrop_path ?: "이미지 없음",
            tv_search.first_air_date ?: "개봉날짜 없음",
            tv_search.vote_average?: "0"
        )

        findNavController().navigate(direction)


    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // handle back button
        requireActivity().onBackPressedDispatcher.addCallback(this) {

            findNavController().navigateUp()
        }

    }


}