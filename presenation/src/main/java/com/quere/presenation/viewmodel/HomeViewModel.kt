package com.quere.presenation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.quere.domain.model.movie.Movie
import com.quere.domain.model.tv.TVshow
import com.quere.presenation.AppConstants
import com.quere.domain.usecase.GetMovieRepoUseCase
import com.quere.domain.usecase.GetTVshowRepoUseCase
import com.quere.presenation.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getMovieRepoUseCase: GetMovieRepoUseCase,
    private val getTVshowRepoUseCase: GetTVshowRepoUseCase
) : ViewModel() {

    suspend fun now_movie() : Flow<PagingData<Movie>> { return getMovieRepoUseCase.getMovie(
        AppConstants.NOW_PLAYING)!!.cachedIn(viewModelScope)}
    suspend fun popular_movie() : Flow<PagingData<Movie>> { return getMovieRepoUseCase.getMovie(AppConstants.POPULAR)!!.cachedIn(viewModelScope)}
    suspend fun upcoming_movie() : Flow<PagingData<Movie>> { return getMovieRepoUseCase.getMovie(AppConstants.UPCOMING)!!.cachedIn(viewModelScope)}
    suspend fun top_movie() : Flow<PagingData<Movie>> { return getMovieRepoUseCase.getMovie(AppConstants.TOP_RATED)!!.cachedIn(viewModelScope)}

    suspend fun onair_tv() : Flow<PagingData<TVshow>> { return getTVshowRepoUseCase.getTvshow(AppConstants.ONTHEAIR)!!.cachedIn(viewModelScope)}
    suspend fun popular_tv() : Flow<PagingData<TVshow>> { return getTVshowRepoUseCase.getTvshow(AppConstants.POPULAR)!!.cachedIn(viewModelScope)}
    suspend fun top_tv() : Flow<PagingData<TVshow>> { return getTVshowRepoUseCase.getTvshow(AppConstants.TOP_RATED)!!.cachedIn(viewModelScope)}


    private val _fragmentHome : MutableLiveData<Event<Unit>> = MutableLiveData()
    val fragmentHome : LiveData<Event<Unit>>
        get() = _fragmentHome

    private val _fragmentMovieGenre : MutableLiveData<Event<Unit>> = MutableLiveData()
    val fragmentMovieGenre : LiveData<Event<Unit>>
        get() = _fragmentMovieGenre

    private val _fragmentTVGenre : MutableLiveData<Event<Unit>> = MutableLiveData()
    val fragmentTVGenre : LiveData<Event<Unit>>
        get() = _fragmentTVGenre

    fun showHome() {
        _fragmentHome.value = Event(Unit)
    }

    fun showMovieGenre() {
        _fragmentMovieGenre.value = Event(Unit)
    }

    fun showTVGenre() {
        _fragmentTVGenre.value = Event(Unit)
    }



}