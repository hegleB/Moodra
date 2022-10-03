package com.quere.data.repository.remote.search

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.quere.data.paging.movie.MovieSearchAllPagingSource
import com.quere.data.paging.movie.MovieSearchPagingSource
import com.quere.data.paging.tv.TVSearchAllPagingSource
import com.quere.data.paging.tv.TVSearchPagingSource
import com.quere.data.repository.remote.movie.PAGE_SIZE
import com.quere.data.service.movieservice.MovieService
import com.quere.data.service.tvservice.TVService
import com.quere.domain.model.movie.Movie
import com.quere.domain.model.tv.TVshow
import com.quere.domain.repository.search.SearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val movieService: MovieService,
    private val tvService: TVService
) : SearchRepository {
    override suspend fun getMovieSearch(query: String): Flow<PagingData<Movie>> {
        return  Pager(PagingConfig(PAGE_SIZE)) {
            MovieSearchPagingSource(movieService, query)
        }.flow
    }

    override suspend fun getTVSearch(query: String): Flow<PagingData<TVshow>> {
        return Pager(PagingConfig(PAGE_SIZE)) {
            TVSearchPagingSource(tvService, query)
        }.flow
    }

    override suspend fun getMovieSearchAll(query: String): Flow<PagingData<Movie>> {
        return Pager(PagingConfig(PAGE_SIZE)) {
            MovieSearchAllPagingSource(movieService, query)
        }.flow
    }

    override suspend fun getTVSearchAll(query: String): Flow<PagingData<TVshow>> {
        return Pager(PagingConfig(PAGE_SIZE)) {
            TVSearchAllPagingSource(tvService, query)
        }.flow
    }
}