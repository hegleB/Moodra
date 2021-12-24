package com.quere.moodra.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.quere.moodra.AppConstants
import com.quere.moodra.repository.TVRepository
import com.quere.moodra.retrofit.TVshow
import kotlinx.coroutines.flow.Flow

class TvViewModel @ViewModelInject constructor(
    private val repo: TVRepository
) : ViewModel() {

    private var currentMovieGenreResult: Flow<PagingData<TVshow>>? = null
    val error = MutableLiveData<String>()

    fun fantasy_tv(): Flow<PagingData<TVshow>> {
        return repo.getTvGenre(AppConstants.FANTASY).cachedIn(viewModelScope)
    }

    fun animation_tv(): Flow<PagingData<TVshow>> {
        return repo.getTvGenre(AppConstants.ANIMATION).cachedIn(viewModelScope)
    }

    fun comedy_tv(): Flow<PagingData<TVshow>> {
        return repo.getTvGenre(AppConstants.COMEDY).cachedIn(viewModelScope)
    }

    fun music_tv(): Flow<PagingData<TVshow>> {
        return repo.getTvGenre(AppConstants.MUSIC).cachedIn(viewModelScope)
    }

    fun romance_tv(): Flow<PagingData<TVshow>> {
        return repo.getTvGenre(AppConstants.ROMANCE).cachedIn(viewModelScope)
    }

    fun crime_tv(): Flow<PagingData<TVshow>> {
        return repo.getTvGenre(AppConstants.CRIME).cachedIn(viewModelScope)
    }

    fun mystery_tv(): Flow<PagingData<TVshow>> {
        return repo.getTvGenre(AppConstants.MYSTERY).cachedIn(viewModelScope)
    }


    fun tv_detail(genre: String): Flow<PagingData<TVshow>> {

        val lastResult = currentMovieGenreResult
        val newResult: Flow<PagingData<TVshow>>
        if (lastResult != null) {
            return lastResult
        }

        when (genre) {

            "판타지" -> {
                newResult =
                    repo.getTvGenreDetail(AppConstants.FANTASY).cachedIn(viewModelScope)
            }
            "애니메이션" -> {
                newResult =
                    repo.getTvGenreDetail(AppConstants.ANIMATION).cachedIn(viewModelScope)
            }
            "코미디" -> {
                newResult =
                    repo.getTvGenreDetail(AppConstants.COMEDY).cachedIn(viewModelScope)
            }
            "뮤직" -> {
                newResult = repo.getTvGenreDetail(AppConstants.MUSIC).cachedIn(viewModelScope)
            }
            "로맨스" -> {
                newResult =
                    repo.getTvGenreDetail(AppConstants.ROMANCE).cachedIn(viewModelScope)
            }
            "범죄" -> {
                newResult = repo.getTvGenreDetail(AppConstants.CRIME).cachedIn(viewModelScope)
            }
            else -> {
                newResult =
                    repo.getTvGenreDetail(AppConstants.MYSTERY).cachedIn(viewModelScope)
            }
        }
        currentMovieGenreResult = newResult

        return newResult
    }

}