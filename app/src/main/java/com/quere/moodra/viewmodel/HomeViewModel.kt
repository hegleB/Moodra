package com.quere.moodra.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.quere.moodra.AppConstants
import com.quere.moodra.repository.MovieRepository
import com.quere.moodra.repository.TVRepository
import com.quere.moodra.retrofit.Movie
import com.quere.moodra.retrofit.TVshow
import kotlinx.coroutines.flow.Flow


class HomeViewModel @ViewModelInject constructor(
    private val movieRepo: MovieRepository,
    private val tvRepo: TVRepository
): ViewModel() {


    fun now_movies() : Flow<PagingData<Movie>> { return movieRepo.getMoviesData(AppConstants.NOW_PLAYING).cachedIn(viewModelScope)}
    fun popular_movies() :Flow<PagingData<Movie>> { return movieRepo.getMoviesData(AppConstants.POPULAR).cachedIn(viewModelScope)}
    fun top_movies() : Flow<PagingData<Movie>>{ return movieRepo.getMoviesData(AppConstants.TOP_RATED).cachedIn(viewModelScope)}
    fun upcoming_movies() :Flow<PagingData<Movie>> { return movieRepo.getMoviesData(AppConstants.UPCOMING).cachedIn(viewModelScope)}

    fun onair_tv(): Flow<PagingData<TVshow>> {return tvRepo.getTvData(AppConstants.ONTHEAIR).cachedIn(viewModelScope)}
    fun popular_tv(): Flow<PagingData<TVshow>> {return tvRepo.getTvData(AppConstants.POPULAR).cachedIn(viewModelScope)}
    fun top_tv(): Flow<PagingData<TVshow>> {return tvRepo.getTvData(AppConstants.TOP_RATED).cachedIn(viewModelScope)}



}