package com.quere.presenation.viewmodel

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
    private val getGenrePopularRepoUseCase: GetGenrePopularRepoUseCase
) : ViewModel() {

    suspend fun getGenre(type:String,genre:String) = getGenreRepoUseCase.getGenre(type,genre)
    suspend fun getGenrePopular(type:String) : Flow<PagingData<Detail>> { return getGenrePopularRepoUseCase.getGenrePopular(
        type)!!.cachedIn(viewModelScope)}


    private val _fragmentHome : MutableLiveData<Event<Unit>> = MutableLiveData()
    val fragmentHome : LiveData<Event<Unit>>
        get() = _fragmentHome

    private val _fragmentMovie : MutableLiveData<Event<Unit>> = MutableLiveData()
    val fragmentMovie : LiveData<Event<Unit>>
        get() = _fragmentMovie

    private val _fragmentTV : MutableLiveData<Event<Unit>> = MutableLiveData()
    val fragmentTv : LiveData<Event<Unit>>
        get() = _fragmentTV


    fun showHome() {
        _fragmentHome.value = Event(Unit)
    }

    fun showMovie() {
        _fragmentMovie.value = Event(Unit)
    }

    fun showTV() {
        _fragmentTV.value = Event(Unit)
    }


}