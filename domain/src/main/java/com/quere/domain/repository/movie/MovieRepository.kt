package com.quere.domain.repository.movie

import androidx.paging.PagingData
import com.quere.domain.model.movie.Movie
import kotlinx.coroutines.flow.Flow


interface MovieRepository {

    suspend fun getMovie(type :String?) : Flow<PagingData<Movie>>?
    suspend fun getMovieDetail(id:Int?) : Movie?

}