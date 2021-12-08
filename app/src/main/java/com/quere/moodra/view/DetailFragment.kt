package com.quere.moodra.view


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
import com.quere.moodra.R
import com.quere.moodra.adapter.*
import com.quere.moodra.databinding.FragmentDetailBinding
import com.quere.moodra.retrofit.*
import com.quere.moodra.room.Bookmark
import com.quere.moodra.viewmodel.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.fragment_detail.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@AndroidEntryPoint
class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    val baseUrl = "https://image.tmdb.org/t/p/w500/"
    val twoDepArgs by navArgs<DetailFragmentArgs>()
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

        when (twoDepArgs.type) {
            "movie" -> getRecyclerViewData(Integer(twoDepArgs.id))
            else -> getRecyclerViewData(Integer(twoDepArgs.id))
        }
        binding.apply {


            Glide.with(detailBackdrop.context).load(baseUrl + twoDepArgs.backdropPath)
                .apply(RequestOptions.bitmapTransform(BlurTransformation(13, 3)))
                .error(R.drawable.movielogo)
                .into(
                    view.detail_backdrop
                )

            detailToolbar.inflateMenu(R.menu.detail_menu)
            val mark = Bookmark(
                twoDepArgs.id,
                twoDepArgs.title,
                twoDepArgs.overview,
                twoDepArgs.isAdult,
                twoDepArgs.posterPath,
                twoDepArgs.backdropPath,
                twoDepArgs.releaseDate,
                twoDepArgs.voteAverage
            )

            var _isCheck = false
            CoroutineScope(Dispatchers.IO).launch {
                val count = detailviewModel.check(mark.id.toString())

                withContext(Dispatchers.Main) {
                    if (count > 0) {
                        _isCheck = true
                        detailToolbar.menu.getItem(0).setIcon(R.drawable.ic_baseline_bookmark_exist)
                    } else {
                        _isCheck = false
                        detailToolbar.menu.getItem(0).setIcon(R.drawable.ic_baseline_bookmark_24)
                    }
                }
            }



            detailToolbar.setOnMenuItemClickListener(object : Toolbar.OnMenuItemClickListener {
                override fun onMenuItemClick(item: MenuItem?): Boolean {
                    when (item!!.itemId) {

                        R.id.menu_bookmark -> {


                            if (_isCheck == true) {
                                _isCheck = false
                                detailToolbar.menu.getItem(0)
                                    .setIcon(R.drawable.ic_baseline_bookmark_24)
                                detailviewModel.delete(mark)
                                Toast.makeText(requireContext(), "북마크 삭제", Toast.LENGTH_LONG).show()
                            } else {
                                _isCheck = true
                                detailviewModel.insert(mark)
                                detailToolbar.menu.getItem(0)
                                    .setIcon(R.drawable.ic_baseline_bookmark_exist)
                                Toast.makeText(requireContext(), "북마크 추가", Toast.LENGTH_LONG).show()
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

            val spannable: Spannable = SpannableString(twoDepArgs.title)
            spannable.setSpan(
                BackgroundColorSpan(
                    resources.getColor(android.R.color.black)
                ), 0, twoDepArgs.title.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )

            detailCollasingtoolbar.title = spannable
            detailReleaseDate.text = twoDepArgs.releaseDate
            if (twoDepArgs.type == "movie") {
                detailviewModel.getMovieDetailData(twoDepArgs.id)
                    .observe(viewLifecycleOwner, Observer {

                        detailRuntime.text = it.runtime.toString() + "분"
                        detailGenre.text = genreParsing(it.genres!!)
                    })
            } else {
                detailviewModel.getTVDetailData(Integer(twoDepArgs.id))
                    .observe(viewLifecycleOwner, Observer {
                        detailGenre.text = genreParsing(it.genres!!)
                    })
            }

            detailOverview.text = twoDepArgs.overview
            Glide.with(detailPoster.context).load(baseUrl + twoDepArgs.posterPath)
                .error(R.drawable.movielogo).into(
                    detailPoster
                )
            detailCollasingtoolbar.setExpandedTitleTextAppearance(R.style.ExpandedTitleText)
            detailCollasingtoolbar.setCollapsedTitleTextAppearance(R.style.CollapsedTitleText)

            var count = (twoDepArgs.voteAverage.toFloat() * 10).toInt()
            simulateProgress(count)

            ContentRecycler(detailSimiliarRecyclerview, "similar")
            ContentRecycler(detailRecommendRecyclerview, "recommend")
            TrailerRecycler(detailTrailerRecyclerview)
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
        if (twoDepArgs.type == "movie") {
            detailviewModel.getMovieCredit(Id).observe(viewLifecycleOwner, Observer {
                CreditRecycler(it, actor_recycler)

            })
        } else {
            detailviewModel.getTVCredit(Id).observe(viewLifecycleOwner, Observer {
                CreditRecycler(it, actor_recycler)
            })
        }

    }

    private fun CreditRecycler(actorList: List<Actor>, recyclerView: RecyclerView) {

        val creditAdapter = CreditAdapter()


        creditAdapter.submitList(actorList)
        recyclerView.adapter = creditAdapter
        recyclerSetting(recyclerView)
    }

    private fun TrailerRecycler(recyclerView: RecyclerView) {

        val adapter = TrailerAdapter({ trailer -> trailerDialog(trailer) },
            { trailer -> trailerDialog(trailer) })


        viewLifecycleOwner.lifecycleScope.launch {
            detailviewModel.getTrailer(twoDepArgs.id).collectLatest {
                adapter.submitData(it)

            }
        }
        recyclerView.adapter = adapter
        recyclerSetting(recyclerView)

    }


    private fun ContentRecycler(recyclerView: RecyclerView, other: String) {
        val adapter = DetailContentsAdapter({ contents -> ContentsDialog(contents) },
            { contents -> ContentsDialog(contents) })

        if (other == "similar") {
            viewLifecycleOwner.lifecycleScope.launch {
                detailviewModel.getSimilar(twoDepArgs.id).collectLatest {
                    adapter.submitData(it)
                }
            }
        } else {
            viewLifecycleOwner.lifecycleScope.launch {
                detailviewModel.getRecommend(twoDepArgs.id).collectLatest {
                    adapter.submitData(it)
                }
            }

        }

        recyclerView.adapter = adapter
        recyclerSetting(recyclerView)
    }


    private fun simulateProgress(count: Int) {
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


    private fun ContentsDialog(movie: OtherContent) {


        val direction = DetailFragmentDirections.actionDetailFragmentSelf(

            "movie",
            movie.title ?: "제목 없음",
            movie.id,
            movie.overview ?: "줄거리 없음",
            false,
            movie.poster_path ?: "이미지 없음",
            movie.backdrop_path ?: "이미지 없음",
            movie.release_date ?: "개봉날짜 없음",
            movie.vote_average ?: "0"

        )

        findNavController().navigate(direction)


    }

    private fun trailerDialog(trailer: Trailer) {

        val args = Bundle()
        args.putString("key", trailer.key)
        val dialogFragment = VideoFragment()
        dialogFragment.setArguments(args)
        dialogFragment.show(requireActivity().supportFragmentManager, "Sample Dialog Fragment")

    }

    private fun recyclerSetting(recyclerView: RecyclerView) {
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerView.addItemDecoration(HorizontalItemDecorator(10))
        recyclerView.setHasFixedSize(true)
    }


    fun genreParsing(genreList: List<Genre>): String {
        var genre = ""
        for (g in genreList) {
            genre += "${g.name}, "
        }
        return if (genre.length >= 2) genre.substring(0, genre.length - 2) else ""

    }


}