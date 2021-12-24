package com.quere.moodra.view.detail


import android.animation.ValueAnimator
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.BackgroundColorSpan
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.quere.moodra.AppConstants
import com.quere.moodra.R
import com.quere.moodra.adapter.*
import com.quere.moodra.databinding.FragmentDetailBinding
import com.quere.moodra.imagebind.ImageBindingAdapter
import com.quere.moodra.retrofit.*
import com.quere.moodra.room.Bookmark
import com.quere.moodra.viewmodel.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@AndroidEntryPoint
class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding

    val DetailArgs by navArgs<DetailFragmentArgs>()
    private val detailviewModel by viewModels<DetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)



        binding = DataBindingUtil.inflate<FragmentDetailBinding>(
            inflater,
            R.layout.fragment_detail,
            container,
            false
        )
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        when (DetailArgs.type) {

            AppConstants.MOVIE -> getRecyclerViewData(Integer(DetailArgs.id))
            else -> getRecyclerViewData(Integer(DetailArgs.id))
        }

        binding.apply {




            detailToolbar.inflateMenu(R.menu.detail_menu)

            val bookmark = Bookmark(
                DetailArgs.id,
                DetailArgs.title,
                DetailArgs.overview,
                DetailArgs.isAdult,
                DetailArgs.posterPath,
                DetailArgs.backdropPath,
                DetailArgs.releaseDate,
                DetailArgs.voteAverage
            )

            var _isCheck = false

            CoroutineScope(Dispatchers.IO).launch {
                val count = detailviewModel.check(bookmark.id.toString())

                withContext(Dispatchers.Main) {
                    if (count > 0) {
                        _isCheck = true

                        setBookmarkImage(R.drawable.ic_baseline_bookmark_exist)


                    } else {
                        _isCheck = false

                        setBookmarkImage(R.drawable.ic_baseline_bookmark_24)
                    }
                }
            }



            detailToolbar.setOnMenuItemClickListener(object : Toolbar.OnMenuItemClickListener {
                override fun onMenuItemClick(item: MenuItem?): Boolean {
                    when (item!!.itemId) {

                        R.id.menu_bookmark -> {


                            if (_isCheck == true) {

                                _isCheck = false
                                setBookmarkImage(R.drawable.ic_baseline_bookmark_24)
                                detailviewModel.delete(bookmark)
                                showToastMessage(R.string.delete_bookmark)

                            } else {

                                _isCheck = true
                                detailviewModel.insert(bookmark)
                                setBookmarkImage(R.drawable.ic_baseline_bookmark_exist)
                                showToastMessage(R.string.add_bookmark)
                            }
                        }

                    }
                    return true
                }

            })


            detailToolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_24)
            detailToolbar.setNavigationOnClickListener(object : View.OnClickListener {
                override fun onClick(p0: View?) {
                    findNavController().navigateUp()
                }

            })

            val spannable: Spannable = SpannableString(DetailArgs.title)
            spannable.setSpan(
                BackgroundColorSpan(
                    resources.getColor(android.R.color.black)
                ), 0, DetailArgs.title.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )

            detailCollasingtoolbar.title = spannable
            detailReleaseDate.text = DetailArgs.releaseDate


            if (DetailArgs.type == AppConstants.MOVIE) {
                detailviewModel.getMovieDetailData(DetailArgs.id)
                    .observe(viewLifecycleOwner, Observer { movieData ->

                        detailRuntime.text = movieData.runtime.toString() + "분"
                        detailGenre.text = getGenreParsing(movieData.genres!!)
                    })
            } else {
                detailviewModel.getTVDetailData(Integer(DetailArgs.id))
                    .observe(viewLifecycleOwner, Observer { tvData ->
                        detailGenre.text = getGenreParsing(tvData.genres!!)
                    })
            }



            detailOverview.text = DetailArgs.overview

            ImageBindingAdapter.detailBackdropImage(detailBackdrop,DetailArgs.backdropPath)
            ImageBindingAdapter.detailPosterImage(detailPoster,DetailArgs.posterPath)

            detailCollasingtoolbar.setExpandedTitleTextAppearance(R.style.ExpandedTitleText)
            detailCollasingtoolbar.setCollapsedTitleTextAppearance(R.style.CollapsedTitleText)



            var count = (DetailArgs.voteAverage.toFloat() * 10).toInt()
            getSimulateProgress(count)

            getContentRecycler(detailSimiliarRecyclerview, AppConstants.SIMILAR)
            getContentRecycler(detailRecommendRecyclerview, AppConstants.RECOMMEND)

            getTrailerRecycler(detailTrailerRecyclerview,DetailArgs.type)
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // handle back button
        requireActivity().onBackPressedDispatcher.addCallback(this) {

            findNavController().navigateUp()
        }

    }

    private fun getRecyclerViewData(Id: Integer) {
        if (DetailArgs.type == AppConstants.MOVIE) {
            detailviewModel.getMovieCredit(Id).observe(viewLifecycleOwner, Observer { MovieActorList ->
                getCreditRecycler(MovieActorList, actor_recycler)

            })
        }

        if(DetailArgs.type == AppConstants.TV){
            detailviewModel.getTVCredit(Id).observe(viewLifecycleOwner, Observer { TvActorList ->
                getCreditRecycler(TvActorList, actor_recycler)
            })
        }

    }

    private fun getCreditRecycler(actorList: List<Actor>, recyclerView: RecyclerView) {

        val creditAdapter = CreditAdapter()


        creditAdapter.submitList(actorList)
        recyclerView.adapter = creditAdapter
        getRecyclerView(recyclerView)
    }

    private fun getTrailerRecycler(recyclerView: RecyclerView,type: String) {

        val adapter = TrailerAdapter({ trailer -> getTrailerDialog(trailer) },
            { trailer -> getTrailerDialog(trailer) })

        if(type==AppConstants.MOVIE) {
            viewLifecycleOwner.lifecycleScope.launch {

                detailviewModel.getMovieTrailer(DetailArgs.id).collectLatest {
                    adapter.submitData(it)

                }
            }
        }


        if(type==AppConstants.TV){

            viewLifecycleOwner.lifecycleScope.launch {
                detailviewModel.getTVTrailer(DetailArgs.id).collectLatest {
                    adapter.submitData(it)

                }
            }
        }
        recyclerView.adapter = adapter
        getRecyclerView(recyclerView)

    }


    private fun getContentRecycler(recyclerView: RecyclerView, other: String) {
        val adapter = DetailContentsAdapter({ contents -> moveDetailToContentDetail(contents) },
            { contents -> moveDetailToContentDetail(contents) })

        if (other == AppConstants.SIMILAR) {
            viewLifecycleOwner.lifecycleScope.launch {
                detailviewModel.getSimilar(DetailArgs.id).collectLatest {
                    adapter.submitData(it)
                }
            }
        }

        if(other == AppConstants.RECOMMEND){
            viewLifecycleOwner.lifecycleScope.launch {
                detailviewModel.getRecommend(DetailArgs.id).collectLatest {
                    adapter.submitData(it)
                }
            }

        }

        recyclerView.adapter = adapter
        getRecyclerView(recyclerView)
    }


    fun getSimulateProgress(count: Int) {
        val animator = ValueAnimator.ofInt(0, count)
        animator.addUpdateListener { animation ->
            val progress = animation.animatedValue as Int
            if (detail_rating != null) {
                detail_rating.setProgress(progress)
            }
        }
        animator.duration = 800
        animator.start()
    }


    fun moveDetailToContentDetail(movie: OtherContent) {


        val DetailToContentDetail = DetailFragmentDirections.actionDetailSelf(

            AppConstants.MOVIE,
            movie.title ?: "제목 없음",
            movie.id,
            movie.overview ?: "줄거리 없음",
            false,
            movie.poster_path ?: "이미지 없음",
            movie.backdrop_path ?: "이미지 없음",
            movie.release_date ?: "개봉날짜 없음",
            movie.vote_average ?: "0"

        )

        findNavController().navigate(DetailToContentDetail)


    }

    fun getTrailerDialog(trailer: Trailer) {

        val args = Bundle()
        args.putString("key", trailer.key)
        val dialogFragment = VideoFragment()
        dialogFragment.setArguments(args)
        dialogFragment.show(requireActivity().supportFragmentManager, "Sample Dialog Fragment")

    }

    fun getRecyclerView(recyclerView: RecyclerView) {
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerView.addItemDecoration(HorizontalItemDecorator(10))
        recyclerView.setHasFixedSize(true)
    }


    fun getGenreParsing(genreList: List<Genre>): String {
        var genre = ""
        for (g in genreList) {
            genre += "${g.name}, "
        }
        return if (genre.length >= 2) genre.substring(0, genre.length - 2) else ""

    }

    fun setBookmarkImage(boomarkImage : Int){
        binding.apply {
            detailToolbar.menu.getItem(0)
                .setIcon(boomarkImage)
        }
    }

    fun showToastMessage(message: Int){
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

}