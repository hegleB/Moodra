package com.quere.presenation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.quere.domain.model.common.Detail
import com.quere.domain.usecase.*
import com.quere.presenation.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class GenreViewModel @Inject constructor(
    private val getGenreRepoUseCase: GetGenreRepoUseCase,
    private val getGenreAllRepoUseCase: GetGenreAllRepoUseCase,
    private val getMoviePopularRepoUseCase: GetMoviePopularRepoUseCase,
    private val getTvPopularRepoUseCase: GetTvPopularRepoUseCase
) : ViewModel() {

    suspend fun getGenre(type:String,genre:String) = getGenreRepoUseCase.getGenre(type,genre)
    suspend fun getGenreAll(type: String, genre: String) = getGenreAllRepoUseCase.getGenreAll(type,genre)
    suspend fun getMoviePopular() : Flow<PagingData<Detail>> { return getMoviePopularRepoUseCase.getMoviePopular()!!.cachedIn(viewModelScope)}
    suspend fun getTVPopular() : Flow<PagingData<Detail>> { return getTvPopularRepoUseCase.getTvPopular()!!.cachedIn(viewModelScope)}

    private val _fragmentHome : MutableLiveData<Event<Unit>> = MutableLiveData()
    val fragmentHome : LiveData<Event<Unit>>
        get() = _fragmentHome

    private val _fragmentMovie : MutableLiveData<Event<Unit>> = MutableLiveData()
    val fragmentMovie : LiveData<Event<Unit>>
        get() = _fragmentMovie

    private val _fragmentTV : MutableLiveData<Event<Unit>> = MutableLiveData()
    val fragmentTv : LiveData<Event<Unit>>
        get() = _fragmentTV

    private val _pageMovieViewPager : MutableLiveData<Int> = MutableLiveData()
    val pageMovieViewPager : LiveData<Int>
        get() = _pageMovieViewPager

    private val _pageTVViewPager : MutableLiveData<Int> = MutableLiveData()
    val pageTVViewPager : LiveData<Int>
        get() = _pageTVViewPager

    private val _animation : MutableLiveData<Event<Unit>> = MutableLiveData()
    val animation : LiveData<Event<Unit>>
        get() = _animation

    private val _fantasy : MutableLiveData<Event<Unit>> = MutableLiveData()
    val fantasy : LiveData<Event<Unit>>
        get() = _fantasy

    private val _action : MutableLiveData<Event<Unit>> = MutableLiveData()
    val action : LiveData<Event<Unit>>
        get() = _action

    private val _music : MutableLiveData<Event<Unit>> = MutableLiveData()
    val music : LiveData<Event<Unit>>
        get() = _music

    private val _comedy : MutableLiveData<Event<Unit>> = MutableLiveData()
    val comedy : LiveData<Event<Unit>>
        get() = _comedy

    private val _romance : MutableLiveData<Event<Unit>> = MutableLiveData()
    val romance : LiveData<Event<Unit>>
        get() = _romance

    private val _crime : MutableLiveData<Event<Unit>> = MutableLiveData()
    val crime : LiveData<Event<Unit>>
        get() = _crime

    private val _mystery : MutableLiveData<Event<Unit>> = MutableLiveData()
    val mystery : LiveData<Event<Unit>>
        get() = _mystery

    private val _horror : MutableLiveData<Event<Unit>> = MutableLiveData()
    val horror : LiveData<Event<Unit>>
        get() = _horror

    private val _genre : MutableLiveData<String> = MutableLiveData()
    val genre : LiveData<String>
        get() = _genre

    fun showHome() {
        _fragmentHome.value = Event(Unit)
    }

    fun showMovie() {
        _fragmentMovie.value = Event(Unit)
    }

    fun showTV() {
        _fragmentTV.value = Event(Unit)
    }

    fun showAnimation() {
        _animation.value = Event(Unit)
    }

    fun showFantasy() {
        _fantasy.value = Event(Unit)
    }

    fun showAction() {
        _action.value = Event(Unit)
    }

    fun showMusic() {
        _music.value = Event(Unit)
    }

    fun showComedy() {
        _comedy.value = Event(Unit)
    }

    fun showRomancee() {
        _romance.value = Event(Unit)
    }

    fun showCrime() {
        _crime.value = Event(Unit)
    }

    fun showMystery() {
        _mystery.value = Event(Unit)
    }

    fun showHorror() {
        _horror.value = Event(Unit)
    }

    fun setPositionMovieViewPager(page:Int) {
        _pageMovieViewPager.postValue(page)

    }

    fun setPositionTVViewPager(page: Int) {
        _pageTVViewPager.postValue(page)
    }

    fun setGenre(genre : String) {
        Log.d("genre", "genre : $genre")
        _genre.postValue(genre)

    }


}