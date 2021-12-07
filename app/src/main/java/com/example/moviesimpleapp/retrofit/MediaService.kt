package com.example.moviesimpleapp.retrofit

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface MediaService {
    @GET("movie/{type}")
    suspend fun getMovie(
        @Path("type") type: String,
        @Query("language") languaue: String,
        @Query("api_key") api_key: String
    ): Movies

    @GET("tv/{type}")
    suspend fun getTV(
        @Path("type") type: String,
        @Query("language") languaue: String,
        @Query("api_key") api_key: String
    ): TVshows

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") movie_id : Int,
        @Query("language") language: String,
        @Query("api_key") api_key: String
    ) : MovieDetail

    @GET("tv/{tv_id}")
    suspend fun getTVDetail(
        @Path("tv_id") tv_id : Integer,
        @Query("language") language: String,
        @Query("api_key") api_key: String
    ) : TVDetail

    @GET("search/movie")
    suspend fun getMovieSeach(
        @Query("api_key") api_key: String,
        @Query("language") languaue: String,
        @Query("query") query: String,
        @Query("page") page: Integer,
    ): search_movie

    @GET("search/tv")
    suspend fun getTVshowSeach(
        @Query("api_key") api_key: String,
        @Query("language") languaue: String,
        @Query("query") query: String,
        @Query("page") page: Integer
    ): search_tvshow

    @GET("tv/{tv_id}/videos")
    suspend fun getTVshowTrailer(
        @Path("tv_id") tvId: Integer,
        @Query("api_key") api_key: String,
        @Query("language") language: String
    ): Trailers

    @GET("movie/{movie_id}/videos")
    suspend fun getMovieTrailer(
        @Path("movie_id") movieId: Int,
        @Query("api_key") api_key: String,
        @Query("language") language: String
    ): Trailers

    @GET("discover/movie")
    suspend fun getMovieGenre(
        @Query("api_key") api_key: String,
        @Query("language") language: String,
        @Query("with_genres") with_genres: String,
        @Query("page") page: Integer
    ): Movies

    @GET("discover/tv")
    suspend fun getTVGenre(
        @Query("api_key") api_key: String,
        @Query("language") languaue: String,
        @Query("with_genres") with_genres: String,
        @Query("page") page: Integer
    ): TVshows

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCredit(
        @Path("movie_id") movieId: Integer,
        @Query("api_key") api_key: String,
        @Query("language") languaue: String
    ): Credit

    @GET("tv/{tv_id}/credits")
    suspend fun getTVCredit(
        @Path("tv_id") tvId: Integer,
        @Query("api_key") api_key: String,
        @Query("language") languaue: String
    ): Credit

    @GET("movie/{movie_id}/similar")
    suspend fun geSimilar(
        @Path("movie_id") movie_id : Int,
        @Query("api_key") api_key:String,
        @Query("language") language:String,
        @Query("page") page:Int
    ) : OtherContents

    @GET("movie/{movie_id}/recommendations")
    suspend fun getRecommendation(
        @Path("movie_id") movie_id : Int,
        @Query("api_key") api_key:String,
        @Query("language") language:String,
        @Query("page") page:Int
    ) : OtherContents
}