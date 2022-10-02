package com.quere.presenation.view.detail

import android.animation.ValueAnimator
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.BackgroundColorSpan
import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.quere.domain.model.common.Bookmark
import com.quere.domain.model.common.Detail
import com.quere.domain.model.common.OtherContent
import com.quere.domain.model.common.Trailer
import com.quere.presenation.AppConstants
import com.quere.presenation.R
import com.quere.presenation.base.BaseFragment
import com.quere.presenation.databinding.FragmentDetailBinding
import com.quere.presenation.view.adapter.*
import com.quere.presenation.viewmodel.BookmarkViewModel
import com.quere.presenation.viewmodel.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>(R.layout.fragment_detail) {

    val detailArgs by navArgs<DetailFragmentArgs>()
    private val detailViewModel: DetailViewModel by activityViewModels()
    private val bookmarkViewModel : BookmarkViewModel by activityViewModels()

    override fun initView() {

        binding.viewmodel = detailViewModel

        initState()
        observeViewModel()
        initRecyclerview()
        getSimulateProgress()
        paintTitle()
        viewModelCallback()

    }

    private fun observeViewModel() {

        detailViewModel.arrowDetail.observe(viewLifecycleOwner) {
            if (it.consumed) return@observe
            findNavController().popBackStack()
            it.consume()
        }

    }

    private fun viewModelCallback() {
        detailViewModel.isBookmark.observe(viewLifecycleOwner) {
            changeBookmarkimage(it)
        }
    }

    private fun initState() {
        detailViewModel.setDetail(detailArgs.detail)
        detailViewModel.checkBookmark()

    }

    private fun initRecyclerview() {
        binding.apply {

            getCreditRecyclerView(
                recyclerViewDetailCredit,
                detailArgs.detail.id?:0,
                detailArgs.detail.type
            )
            getTrailerRecyclerView(
                recyclerViewDetailTrailer,
                detailArgs.detail.id?:0,
                detailArgs.detail.type
            )
            getContentRecyclerView(
                recyclerViewDetailSimiliar,
                detailArgs.detail.id?:0,
                AppConstants.SIMILAR,
                detailArgs.detail.type
            )
            getContentRecyclerView(
                recyclerViewDetailRecommend,
                detailArgs.detail.id?:0,
                AppConstants.RECOMMEND,
                detailArgs.detail.type
            )
        }
    }

    private fun paintTitle() {
        var title = ""

        if (detailArgs.detail.type == "movie") {
            title = detailArgs.detail.title?:""
        } else {
            title = detailArgs.detail.name?:""
        }
        
        val spannable: Spannable = SpannableString(title)
        spannable.setSpan(
            BackgroundColorSpan(
                resources.getColor(android.R.color.black)
            ), 0, title.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        binding.collasingtoolBarDetail.title = spannable
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
                ItemClick = { showtrailerDialog(it) }
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

        when (detailArgs.detail.type) {
            "movie" -> {
                val deailtTotherdetail = DetailFragmentDirections.actionDetailFragmentSelf(
                    Detail(
                        "movie",
                        content.title?:"",
                        "",
                        listOf(),
                        content.id,
                        content.overview?:"",
                        false,
                        content.poster_path?:"",
                        content.backdrop_path?:"",
                        content.release_date?:"",
                        0,
                        content.video,
                        content.vote_average?:""
                    )
                )
                findNavController().navigate(deailtTotherdetail)
            }
            else -> {
                val deailtTotherdetail = DetailFragmentDirections.actionDetailFragmentSelf(
                    Detail(
                        "tv",
                        "",
                        content.name?:"",
                        listOf(),
                        content.id,
                        content.overview?:"",
                        false,
                        content.poster_path?:"",
                        content.backdrop_path?:"",
                        content.first_air_date?:"",
                        0,
                        content.video,
                        content.vote_average?:""
                    )
                )
                findNavController().navigate(deailtTotherdetail)
            }
        }


    }


    fun getSimulateProgress() {

        val voteAverage = detailArgs.detail.vote_average ?: "0"
        val count = (voteAverage.toFloat()*10).toInt()

        val animator = ValueAnimator.ofInt(0, count)
        animator.addUpdateListener { animation ->
            val progress = animation.animatedValue as Int
            if (binding.circleProgressBarDetailRating != null) {
                binding.circleProgressBarDetailRating.setProgress(progress)
            }
        }
        animator.duration = 800
        animator.start()
    }

    private fun showtrailerDialog(trailer: Trailer) {

        val args = Bundle()
        args.putString("key", trailer.key)
        val dialogFragment = VideoFragment()
        dialogFragment.setArguments(args)
        dialogFragment.show(requireActivity().supportFragmentManager, "Sample Dialog Fragment")

    }

    private fun changeBookmarkimage(isBookmark : Boolean) {
        if (isBookmark) {
            binding.imageViewDetailBookmark.setImageResource(R.drawable.ic_baseline_bookmark_exist)
        } else {
            binding.imageViewDetailBookmark.setImageResource(R.drawable.ic_baseline_bookmark_24)
        }
    }


}