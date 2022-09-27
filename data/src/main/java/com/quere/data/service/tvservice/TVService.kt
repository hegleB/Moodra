package com.quere.data.service.tvservice

import com.quere.domain.model.common.Credit
import com.quere.domain.model.common.Trailers
import com.quere.domain.model.tv.TVshow
import com.quere.domain.model.tv.TVshows
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TVService {

    @GET("tv/{type}")
    suspend fun getTV(
        @Path("type") type: String

    ): TVshows

    @GET("tv/{tv_id}")
    suspend fun getTVDetail(
        @Path("tv_id") tv_id : Int
    ) : TVshow

    @GET("search/tv")
    suspend fun getTVshowSeach(
        @Query("query") query: String,
        @Query("page") page: Integer
    ): TVshows

    @GET("tv/{tv_id}/videos")
    suspend fun getTVshowTrailer(
        @Path("tv_id") tvId: Int
    ): Trailers

    @GET("discover/tv")
    suspend fun getTVGenre(
        @Query("with_genres") with_genres: String,
        @Query("page") page: Integer
    ): TVshows

    @GET("tv/{tv_id}/credits")
    suspend fun getTVCredit(
        @Path("tv_id") tvId: Integer
    ): Credit

}