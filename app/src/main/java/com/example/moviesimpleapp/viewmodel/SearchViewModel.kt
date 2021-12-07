package com.example.moviesimpleapp.viewmodel

import android.app.Application
import android.os.Parcelable
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.moviesimpleapp.AppConstants
import com.example.moviesimpleapp.repository.Repository
import com.example.moviesimpleapp.retrofit.Actor
import com.example.moviesimpleapp.retrofit.MovieSearch
import com.example.moviesimpleapp.retrofit.TVshowSearch
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class SearchViewModel @ViewModelInject constructor(
    private val repository: Repository

): ViewModel(){

    val error = MutableLiveData<String>()


    private val _query = MutableLiveData<String>()
    private var currentMovieSearchResult: Flow<PagingData<MovieSearch>>?=null
    private var currentTVSearchResult: Flow<PagingData<TVshowSearch>>?=null

    val query : LiveData<String> get() = _query


    fun setQuery(query: String) {
        _query.value = query
    }


    fun getMovies(query: String) : Flow<PagingData<MovieSearch>> {

        return repository.getMovieSearchData(query).cachedIn(viewModelScope)
    }

    fun getMoviesDetail(query: String) : Flow<PagingData<MovieSearch>> {

        val lastResult=currentMovieSearchResult

        if(lastResult!=null){
            return lastResult
        }

        val newResult: Flow<PagingData<MovieSearch>> = repository.getMovieSearchDetaileData(query).cachedIn(viewModelScope)

        currentMovieSearchResult=newResult

        return newResult
    }


    fun getTvshow(query: String) : Flow<PagingData<TVshowSearch>> {

        return repository.getTVSearchData(query).cachedIn(viewModelScope)

    }

    fun getTvshowDetail(query: String) : Flow<PagingData<TVshowSearch>> {
        val lastResult=currentTVSearchResult

        if(lastResult!=null){
            return lastResult
        }

        val newResult: Flow<PagingData<TVshowSearch>> = repository.getTVSearchDetailData(query).cachedIn(viewModelScope)

        currentTVSearchResult=newResult

        return newResult

    }


}