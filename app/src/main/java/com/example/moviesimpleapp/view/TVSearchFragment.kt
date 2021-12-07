package com.example.moviesimpleapp.view

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible

import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.example.moviesimpleapp.R
import com.example.moviesimpleapp.adapter.MovieSearchLoadStateAdapter
import com.example.moviesimpleapp.adapter.TVSearchDetailAdapter
import com.example.moviesimpleapp.databinding.FragmentSearchTvBinding
import com.example.moviesimpleapp.retrofit.TVshowSearch
import com.example.moviesimpleapp.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TVSearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchTvBinding
    private val viewModel by viewModels<SearchViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            LayoutInflater.from(requireContext()),
            R.layout.fragment_search_tv,
            container,
            false
        )

        (requireActivity() as AppCompatActivity).supportActionBar?.show()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = TVSearchDetailAdapter({ TVSearch -> TVshowSearchDialog(TVSearch) },
            { TVSearch -> TVshowSearchDialog(TVSearch) })
        adapter.notifyDataSetChanged()
        binding.apply {
            tvSearchRecyclerView.setHasFixedSize(true)
            tvSearchRecyclerView.itemAnimator = null
            tvSearchRecyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
                header = MovieSearchLoadStateAdapter { adapter.retry() },
                footer = MovieSearchLoadStateAdapter { adapter.retry() }
            )


        }

        adapter.addLoadStateListener { loadState ->
            binding.apply {
                tvProcessBar.isVisible = loadState.source.refresh is LoadState.Loading
                tvSearchRecyclerView.isVisible = loadState.source.refresh is LoadState.NotLoading

                // empty view
                if (loadState.source.refresh is LoadState.NotLoading &&
                    loadState.append.endOfPaginationReached &&
                    adapter.itemCount < 1
                ) {
                    tvSearchRecyclerView.isVisible = false
                    tvTextViewEmpty.isVisible = true

                } else {
                    tvTextViewEmpty.isVisible = false
                }
            }
        }


        requireActivity().supportFragmentManager.setFragmentResultListener(
            "requestKey2",
            viewLifecycleOwner
        ) { key, bundle ->
            bundle.getString("key2")?.let {

                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.getTvshow(it).collectLatest {
                        adapter.submitData(it)
                    }
                }
            }
        }



    }


    private fun TVshowSearchDialog(tv_search: TVshowSearch) {


        val direction = SearchFragmentDirections.actionSearchFragmentToDetailFragment(
            "tv",
            tv_search.name ?: "제목 없음",
            tv_search.id,
            tv_search.overview ?: "줄거리 없음",
            false,
            tv_search.poster_path ?: "이미지 없음",
            tv_search.poster_path ?: "이미지 없음",
            tv_search.first_air_date ?: "방영날짜 없음",
            tv_search.vote_average?: "0"
        )
        findNavController().navigate(direction)




    }

}