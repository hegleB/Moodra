package com.quere.presenation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.quere.domain.model.movie.Movie
import com.quere.domain.model.tv.TVshow
import com.quere.domain.usecase.GetMovieSearchAllRepoUseCase
import com.quere.domain.usecase.GetMovieSearchRepoUseCase
import com.quere.domain.usecase.GetTVSearchAllRepoUseCase
import com.quere.domain.usecase.GetTVSearchRepoUseCase
import com.quere.presenation.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getMovieSearchRepoUseCase: GetMovieSearchRepoUseCase,
    private val getTVSearchRepoUseCase: GetTVSearchRepoUseCase,
    private val getMovieSearchAllRepoUseCase: GetMovieSearchAllRepoUseCase,
    private val getTVSearchAllRepoUseCase: GetTVSearchAllRepoUseCase
) : ViewModel() {

    private var currentMovieSearchResult: Flow<PagingData<Movie>>? = null
    private var currentTVSearchResult: Flow<PagingData<TVshow>>? = null

    suspend fun getMovieSearch(query: String): Flow<PagingData<Movie>> {
        return getMovieSearchRepoUseCase.getMovieSearch(query).cachedIn(viewModelScope)
    }

    suspend fun getTVSearch(query: String): Flow<PagingData<TVshow>> {
        return getTVSearchRepoUseCase.getTVSearch(query).cachedIn(viewModelScope)
    }

    suspend fun getMovieSearchAll(query: String): Flow<PagingData<Movie>> {
        var lastResult = currentMovieSearchResult

        if (lastResult != null) {
            return lastResult
        }

        val newResult: Flow<PagingData<Movie>> =
            getMovieSearchAllRepoUseCase.getMovieSearchAll(query).cachedIn(viewModelScope)
        currentMovieSearchResult = newResult

        return newResult
    }

    suspend fun getTVSearchAll(query: String): Flow<PagingData<TVshow>> {
        var lastResult = currentTVSearchResult

        if (lastResult != null) {
            return lastResult
        }

        val newResult: Flow<PagingData<TVshow>> =
            getTVSearchAllRepoUseCase.getTVSearchAll(query).cachedIn(viewModelScope)
        currentTVSearchResult = newResult

        return newResult
    }

    private val _movieSearchAll: MutableLiveData<Event<Unit>> = MutableLiveData()
    val movieSearchAll: LiveData<Event<Unit>>
        get() = _movieSearchAll

    private val _tvSearchAll: MutableLiveData<Event<Unit>> = MutableLiveData()
    val tvSearchAll: LiveData<Event<Unit>>
        get() = _tvSearchAll

    private val _searchQuery: MutableLiveData<String> = MutableLiveData()
    val searchQuery: LiveData<String>
        get() = _searchQuery


    fun showMovieSearchAll() {
        _movieSearchAll.value = Event(Unit)
    }

    fun showTVSearchAll() {
        _tvSearchAll.value = Event(Unit)
    }

    fun setSearchQuery(query: String) {
        currentMovieSearchResult=null
        currentTVSearchResult=null
        _searchQuery.value = query
    }




}