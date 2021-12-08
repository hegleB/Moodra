package com.quere.moodra.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.quere.moodra.AppConstants
import com.quere.moodra.repository.Repository
import com.quere.moodra.retrofit.Movie
import kotlinx.coroutines.flow.Flow

class MovieViewModel @ViewModelInject constructor(
    private val repository: Repository
) : ViewModel() {


    private var currentMovieGenreResult: Flow<PagingData<Movie>>? = null
    val error = MutableLiveData<String>()

    fun action_movies(): Flow<PagingData<Movie>> {
        return repository.getMovieGenre(AppConstants.ACTION).cachedIn(viewModelScope)
    }

    fun fantasy_movies(): Flow<PagingData<Movie>> {
        return repository.getMovieGenre(AppConstants.FANTASY).cachedIn(viewModelScope)
    }

    fun animation_movies(): Flow<PagingData<Movie>> {
        return repository.getMovieGenre(AppConstants.ANIMATION).cachedIn(viewModelScope)
    }

    fun comedy_movies(): Flow<PagingData<Movie>> {
        return repository.getMovieGenre(AppConstants.COMEDY).cachedIn(viewModelScope)
    }

    fun music_movies(): Flow<PagingData<Movie>> {
        return repository.getMovieGenre(AppConstants.MUSIC).cachedIn(viewModelScope)
    }

    fun romance_movies(): Flow<PagingData<Movie>> {
        return repository.getMovieGenre(AppConstants.ROMANCE).cachedIn(viewModelScope)
    }

    fun crime_movies(): Flow<PagingData<Movie>> {
        return repository.getMovieGenre(AppConstants.CRIME).cachedIn(viewModelScope)
    }

    fun mystery_movies(): Flow<PagingData<Movie>> {
        return repository.getMovieGenre(AppConstants.MYSTERY).cachedIn(viewModelScope)
    }

    fun horror_movies(): Flow<PagingData<Movie>> {
        return repository.getMovieGenre(AppConstants.HORROR).cachedIn(viewModelScope)
    }


    fun movies_detail(genre: String): Flow<PagingData<Movie>> {
        val lastResult = currentMovieGenreResult
        val newResult: Flow<PagingData<Movie>>
        if (lastResult != null) {
            return lastResult
        }

        when (genre) {

            "액션" -> {

                newResult =
                    repository.getMovieGenreDetail(AppConstants.ACTION).cachedIn(viewModelScope)

            }
            "판타지" -> {

                newResult =
                    repository.getMovieGenreDetail(AppConstants.FANTASY).cachedIn(viewModelScope)


            }
            "애니메이션" -> {

                newResult =
                    repository.getMovieGenreDetail(AppConstants.ANIMATION).cachedIn(viewModelScope)


            }
            "코미디" -> {

                newResult =
                    repository.getMovieGenreDetail(AppConstants.COMEDY).cachedIn(viewModelScope)


            }
            "뮤직" -> {

                newResult =
                    repository.getMovieGenreDetail(AppConstants.MUSIC).cachedIn(viewModelScope)


            }
            "로맨스" -> {

                newResult =
                    repository.getMovieGenreDetail(AppConstants.ROMANCE).cachedIn(viewModelScope)


            }
            "범죄" -> {

                newResult =
                    repository.getMovieGenreDetail(AppConstants.CRIME).cachedIn(viewModelScope)


            }
            "미스테리" -> {

                newResult =
                    repository.getMovieGenreDetail(AppConstants.MYSTERY).cachedIn(viewModelScope)


            }
            else -> {

                newResult =
                    repository.getMovieGenreDetail(AppConstants.HORROR).cachedIn(viewModelScope)


            }

        }
        currentMovieGenreResult = newResult
        return newResult
    }


}