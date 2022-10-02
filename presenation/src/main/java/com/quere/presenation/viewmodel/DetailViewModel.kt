package com.quere.presenation.viewmodel

import android.R
import android.text.Spannable
import android.text.SpannableString
import android.text.style.BackgroundColorSpan
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.quere.domain.model.common.*
import com.quere.domain.model.movie.Movie
import com.quere.domain.model.tv.TVshow
import com.quere.domain.usecase.*
import com.quere.presenation.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getSimilarRepoUseCase: GetSimilarRepoUseCase,
    private val getRecommendRepoUseCase: GetRecommendRepoUseCase,
    private val getMovieDetailRepoUseCase: GetMovieDetailRepoUseCase,
    private val getTrailerRepoUseCase: GetTrailerRepoUseCase,
    private val getCreditRepoUseCase: GetCreditRepoUseCase,
    private val getTVDetailRepoUseCase: GetTVDetailRepoUseCase,
    private val checkBookmarkUseCase : CheckBookmarkUseCase,
    private val deleteBookmarkUseCase: DeleteBookmarkUseCase,
    private val saveBookmarkUseCase: SaveBookmarkUseCase
) : ViewModel() {

    suspend fun getMovieDetail(id: Int): Movie {
        return getMovieDetailRepoUseCase.getMovieDetail(
            id
        )!!
    }

    suspend fun getTVDetail(id: Int): TVshow {
        return getTVDetailRepoUseCase.getTVDetail(
            id
        )!!
    }

    suspend fun getCredit(type: String, id: Int): Credit {
        return getCreditRepoUseCase.getCredit(
            type,
            id
        )!!
    }

    suspend fun getTrailer(type: String, id: Int): Trailers {
        return getTrailerRepoUseCase.getTrailer(type, id)!!
    }

    suspend fun getsimilar(type: String, id: Int): Flow<PagingData<OtherContent>> {
        return getSimilarRepoUseCase.getSimilar(
            type, id
        )!!.cachedIn(viewModelScope)
    }

    suspend fun getrecommend(type: String, id: Int): Flow<PagingData<OtherContent>> {
        return getRecommendRepoUseCase.getRecommend(
            type, id
        )!!.cachedIn(viewModelScope)
    }

    private val _type: MutableLiveData<String> = MutableLiveData()
    val type: LiveData<String>
        get() = _type

    private val _title: MutableLiveData<String> = MutableLiveData()
    val title: LiveData<String>
        get() = _title

    private val _name: MutableLiveData<String> = MutableLiveData()
    val name: LiveData<String>
        get() = _name


    private val _genre: MutableLiveData<List<Genre>> = MutableLiveData()
    val genre: LiveData<List<Genre>>
        get() = _genre

    private val _id: MutableLiveData<Int> = MutableLiveData()
    val id: LiveData<Int>
        get() = _id

    private val _overview: MutableLiveData<String> = MutableLiveData()
    val overview: LiveData<String>
        get() = _overview

    private val _isAdult: MutableLiveData<Boolean> = MutableLiveData()
    val isAdult: LiveData<Boolean>
        get() = _isAdult

    private val _postPath: MutableLiveData<String> = MutableLiveData()
    val postPath: LiveData<String>
        get() = _postPath

    private val _backdropPath: MutableLiveData<String> = MutableLiveData()
    val backdropPath: LiveData<String>
        get() = _backdropPath

    private val _releaseDate: MutableLiveData<String> = MutableLiveData()
    val releaseDate: LiveData<String>
        get() = _releaseDate

    private val _runtime: MutableLiveData<Int> = MutableLiveData()
    val runtime: LiveData<Int>
        get() = _runtime

    private val _video: MutableLiveData<Boolean> = MutableLiveData()
    val video: LiveData<Boolean>
        get() = _video

    private val _voteAverage: MutableLiveData<Int> = MutableLiveData()
    val voteAverage: LiveData<Int>
        get() = _voteAverage

    private val _arrowDetail: MutableLiveData<Event<Unit>> = MutableLiveData()
    val arrowDetail: LiveData<Event<Unit>>
        get() = _arrowDetail

    private val _isBookmark = MutableLiveData<Boolean>(false)
    val isBookmark : LiveData<Boolean>
        get() = _isBookmark

    private val _deleteBookmark = MutableLiveData<Bookmark>()
    val deleteBookmark : LiveData<Bookmark> = _deleteBookmark

    fun setDetail(detail: Detail) {
        _deleteBookmark.value = Bookmark(detail.id, detail.type, detail.title, detail.name, detail.overview, detail.is_adult,
        detail.poster_path, detail.backdrop_path, detail.release_date, detail.runtime, detail.video, detail.vote_average)
        _type.value = detail.type
        _name.value = detail.name
        _title.value = detail.title
        _genre.value = detail.genres
        _id.value = detail.id
        _overview.value = detail.overview
        _isAdult.value = detail.is_adult
        _postPath.value = detail.poster_path
        _backdropPath.value = detail.backdrop_path
        _releaseDate.value = detail.release_date
        _runtime.value = detail.runtime
        _video.value = detail.video
        _voteAverage.value = (detail.vote_average!!.toFloat() * 10).toInt()

    }

    fun checkBookmark() = viewModelScope.launch {
        _isBookmark.value = checkBookmarkUseCase.checkBookmark(_id.value.toString() ?: "") ?: false
    }

    fun uptateSavedState() {
        when (_isBookmark.value) {
            true -> {
                deleteSaved()
                _isBookmark.value = false
            }
            else -> {
                saveBookmark(_deleteBookmark.value?:Bookmark())
                _isBookmark.value = true
            }

        }
    }

    private fun deleteSaved() = viewModelScope.launch {
        if (_deleteBookmark.value != null) deleteBookmarkUseCase.deleteBookmarkUseCase(_deleteBookmark.value!!)
    }

    private fun saveBookmark(bookmark: Bookmark) = viewModelScope.launch {
        if (bookmark!=null) {
            saveBookmarkUseCase.saveBookmarkUseCase(bookmark)
        }
    }

    fun backDetail() {
        _arrowDetail.value = Event(Unit)
    }






}