package com.quere.data.service.commonservice


import com.quere.domain.model.common.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CommonService  {

    @GET("{type}/{id}/similar")
    suspend fun getSimilar(
        @Path("type") type: String,
        @Path("id") id : Int,
        @Query("page") page:Int
    ) : OtherContents

    @GET("{type}/{id}/recommendations")
    suspend fun getRecommendation(
        @Path("type") type:String,
        @Path("id") id : Int,
        @Query("page") page:Int
    ) : OtherContents

    @GET("{type}/{id}/credits")
    suspend fun getCredit(
        @Path("type") type: String,
        @Path("id") Id: Int
    ): Credit

    @GET("{type}/{id}/videos")
    suspend fun getTrailer(
        @Path("type") type:String,
        @Path("id") Id: Int
    ): Trailers

    @GET("discover/{type}")
    suspend fun getGenre(
        @Path("type") type:String,
        @Query("with_genres") with_genres: String,
        @Query("page") page: Int
    ): Genres

    @GET("{type}/popular")
    suspend fun getGenrePopular(
        @Path("type") type: String
    ): Genres

}