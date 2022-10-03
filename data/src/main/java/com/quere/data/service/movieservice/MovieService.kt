package com.quere.data.service.movieservice

import com.quere.domain.model.movie.Movie
import com.quere.domain.model.movie.Movies
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    @GET("movie/{type}")
    suspend fun getMovie(
        @Path("type") type: String
    ): Movies

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") movie_id : Int,
    ) : Movie

    @GET("search/movie")
    suspend fun getMovieSeach(
        @Query("query") query: String,
        @Query("page") page: Int,
    ): Movies
}