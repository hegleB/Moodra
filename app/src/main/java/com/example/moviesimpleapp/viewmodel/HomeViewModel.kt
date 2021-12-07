package com.example.moviesimpleapp.viewmodel

import android.app.Application
import android.os.Parcelable
import android.util.MutableDouble
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.moviesimpleapp.AppConstants
import com.example.moviesimpleapp.repository.Repository
import com.example.moviesimpleapp.retrofit.Movie
import com.example.moviesimpleapp.retrofit.TVshow
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow


class HomeViewModel @ViewModelInject constructor(
    private val repository: Repository
): ViewModel() {


    fun now_movies() : Flow<PagingData<Movie>> { return repository.getMoviesData(AppConstants.NOW_PLAYING).cachedIn(viewModelScope)}
    fun popular_movies() :Flow<PagingData<Movie>> { return repository.getMoviesData(AppConstants.POPULAR).cachedIn(viewModelScope)}
    fun top_movies() : Flow<PagingData<Movie>>{ return repository.getMoviesData(AppConstants.TOP_RATED).cachedIn(viewModelScope)}
    fun upcoming_movies() :Flow<PagingData<Movie>> { return repository.getMoviesData(AppConstants.UPCOMING).cachedIn(viewModelScope)}

    fun onair_tv(): Flow<PagingData<TVshow>> {return repository.getTvData(AppConstants.ONTHEAIR).cachedIn(viewModelScope)}
    fun popular_tv(): Flow<PagingData<TVshow>> {return repository.getTvData(AppConstants.POPULAR).cachedIn(viewModelScope)}
    fun top_tv(): Flow<PagingData<TVshow>> {return repository.getTvData(AppConstants.TOP_RATED).cachedIn(viewModelScope)}



}