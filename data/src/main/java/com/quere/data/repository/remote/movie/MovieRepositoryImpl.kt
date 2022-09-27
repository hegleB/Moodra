package com.quere.data.repository.remote.movie

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.quere.data.paging.movie.MoviePagingSource
import com.quere.data.service.movieservice.MovieService
import com.quere.domain.model.common.*
import com.quere.domain.model.movie.Movie
import com.quere.domain.repository.movie.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

const val PAGE_SIZE = 30

class MovieRepositoryImpl @Inject constructor(
    private val movieService: MovieService
) : MovieRepository {
    override suspend fun getMovie(type: String?): Flow<PagingData<Movie>> {
        return Pager(PagingConfig(PAGE_SIZE)) {
            MoviePagingSource(movieService, type!!)
        }.flow
    }

    override suspend fun getMovieDetail(id: Int?): Movie? {
        return movieService.getMovieDetail(id!!)
    }

}