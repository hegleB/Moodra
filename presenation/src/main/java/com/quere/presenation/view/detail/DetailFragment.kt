package com.quere.presenation.view.detail

import android.animation.ValueAnimator
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.BackgroundColorSpan
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.quere.domain.model.common.Detail
import com.quere.domain.model.common.OtherContent
import com.quere.domain.model.common.Trailer
import com.quere.presenation.AppConstants
import com.quere.presenation.R
import com.quere.presenation.base.BaseFragment
import com.quere.presenation.databinding.FragmentDetailBinding
import com.quere.presenation.view.adapter.*
import com.quere.presenation.viewmodel.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>(R.layout.fragment_detail) {

    val DetailArgs by navArgs<DetailFragmentArgs>()
    private val detailViewModel: DetailViewModel by activityViewModels()
    private lateinit var type: String
    private lateinit var title : String

    override fun initView() {

        binding.detail = DetailArgs.detail

        viewLifecycleOwner.lifecycleScope.launch {
            when (DetailArgs.detail.type) {
                true -> {

                    binding.detailMovie = detailViewModel.getMovieDetail(DetailArgs.detail.id!!)
                }
                else -> {
                    binding.detailTV = detailViewModel.getTVDetail(DetailArgs.detail.id!!)
                }
            }


        }
        if (DetailArgs.detail.type) {
            type = "movie"
            title = DetailArgs.detail.title!!
        } else {
            type = "tv"
            title = DetailArgs.detail.name!!
        }

        val spannable: Spannable = SpannableString(title)
        spannable.setSpan(
            BackgroundColorSpan(
                resources.getColor(android.R.color.black)
            ), 0, title.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        getSimulateProgress((DetailArgs.detail.vote_average!!.toFloat() * 10).toInt())

        binding.apply {
            detailArrowBack.setOnClickListener { it.findNavController().popBackStack() }
            detailCollasingtoolbar.title = spannable



            getCreditRecyclerView(detailCreditRecycler, DetailArgs.detail.id!!, type)
            getTrailerRecyclerView(detailTrailerRecyclerview, DetailArgs.detail.id!!, type)
            getContentRecyclerView(
                detailSimiliarRecyclerview,
                DetailArgs.detail.id!!,
                AppConstants.SIMILAR,
                type
            )
            getContentRecyclerView(
                detailRecommendRecyclerview,
                DetailArgs.detail.id!!,
                AppConstants.RECOMMEND,
                type
            )
        }
    }

    private fun getCreditRecyclerView(recyclerView: RecyclerView, Id: Int, type: String) {
        val creditAdapter: CreditAdapter by lazy {
            CreditAdapter()
        }

        recyclerView.adapter = creditAdapter

        recyclerView.addItemDecoration(HorizontalItemDecorator(10))

        viewLifecycleOwner.lifecycleScope.launch {
            detailViewModel.getCredit(type, Id).cast.run {
                creditAdapter.submitList(this)
            }
        }
    }


    private fun getTrailerRecyclerView(recyclerView: RecyclerView, Id: Int, type: String) {
        val trailerAdapter: TrailerAdapter by lazy {
            TrailerAdapter(
                ItemClick = { trailerDialog(it) }
            )
        }
        recyclerView.adapter = trailerAdapter
        recyclerView.addItemDecoration(HorizontalItemDecorator(10))

        viewLifecycleOwner.lifecycleScope.launch {

            detailViewModel.getTrailer(type, Id).results.run {
                trailerAdapter.submitList(this)
            }

        }
    }

    private fun getContentRecyclerView(
        recyclerView: RecyclerView,
        Id: Int,
        contet: String,
        type: String
    ) {
        val contentsAdapter: ContentsAdapter by lazy {
            ContentsAdapter(
                ItemClick = { doOnClick(it) }
            )
        }

        recyclerView.adapter = contentsAdapter
        recyclerView.addItemDecoration(HorizontalItemDecorator(10))


        when (contet) {

            AppConstants.SIMILAR -> {

                viewLifecycleOwner.lifecycleScope.launch {
                    detailViewModel.getsimilar(type, Id).collectLatest { contentList ->
                        contentsAdapter.submitData(contentList)
                    }
                }
            }
            AppConstants.RECOMMEND -> {
                viewLifecycleOwner.lifecycleScope.launch {
                    detailViewModel.getrecommend(type, Id).collectLatest { contentList ->
                        contentsAdapter.submitData(contentList)
                    }
                }
            }

        }

    }

    private fun doOnClick(content: OtherContent) {

        when(type){
            "movie" -> {
                val deailtTotherdetail = DetailFragmentDirections.actionDetailFragmentSelf(
                    Detail(
                        true,
                        content.title!!,
                        "",
                        listOf(),
                        content.id,
                        content.overview!!,
                        false,
                        content.poster_path!!,
                        content.backdrop_path!!,
                        content.release_date!!,
                        0,
                        content.video,
                        content.vote_average!!
                    )
                )
                findNavController().navigate(deailtTotherdetail)
            } else -> {
            val deailtTotherdetail = DetailFragmentDirections.actionDetailFragmentSelf(
                Detail(
                    false,
                    "",
                    content.name!!,
                    listOf(),
                    content.id,
                    content.overview!!,
                    false,
                    content.poster_path!!,
                    content.backdrop_path!!,
                    content.first_air_date!!,
                    0,
                    content.video,
                    content.vote_average!!
                )
            )
            findNavController().navigate(deailtTotherdetail)
            }
        }


    }


    fun getSimulateProgress(count: Int) {
        val animator = ValueAnimator.ofInt(0, count)
        animator.addUpdateListener { animation ->
            val progress = animation.animatedValue as Int
            if (binding.detailRating != null) {
                binding.detailRating.setProgress(progress)
            }
        }
        animator.duration = 800
        animator.start()
    }

    private fun trailerDialog(trailer: Trailer) {

        val args = Bundle()
        args.putString("key", trailer.key)
        val dialogFragment = VideoFragment()
        dialogFragment.setArguments(args)
        dialogFragment.show(requireActivity().supportFragmentManager, "Sample Dialog Fragment")

    }


}