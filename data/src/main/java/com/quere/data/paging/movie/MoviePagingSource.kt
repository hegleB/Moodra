package com.quere.data.paging.movie

import com.quere.data.base.BasePagingSource
import com.quere.data.service.movieservice.MovieService
import com.quere.domain.model.movie.Movie


class MoviePagingSource(
    private val service: MovieService,
    private val type : String?


) : BasePagingSource<Movie>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {

        dataList = service.getMovie(type!!).results

        return super.load(params)
    }
}