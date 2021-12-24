package com.quere.moodra.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.quere.moodra.paging.*
import com.quere.moodra.retrofit.*
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TVRepository @Inject constructor(
    val mediaService: MediaService
){

    fun getTvData(tvType: String): Flow<PagingData<TVshow>> {
        return Pager(
            config = PagingConfig(
                pageSize = 30,
                maxSize = 100,
                enablePlaceholders = false,
                initialLoadSize = 10
            ),
            pagingSourceFactory = { TVPagingSource(mediaService, tvType) }
        ).flow
    }


    fun getTvGenreDetail(tv_genre: String): Flow<PagingData<TVshow>> {
        return Pager(
            config = PagingConfig(
                pageSize = 30,
                maxSize = 100,
                enablePlaceholders = false,
                initialLoadSize = 10

            ),
            pagingSourceFactory = { TVGenreDetailPagingSource(mediaService, tv_genre) }
        ).flow
    }


    fun getTVSearchData(query: String): Flow<PagingData<TVshowSearch>> {
        return Pager(
            config = PagingConfig(
                pageSize = 30,
                maxSize = 100,
                enablePlaceholders = false,
                initialLoadSize = 10

            ),

            pagingSourceFactory = { TVSearchPagingSource(mediaService, query) }
        ).flow
    }


    fun getTVSearchDetailData(query: String): Flow<PagingData<TVshowSearch>> {
        return Pager(
            config = PagingConfig(
                pageSize = 30,
                maxSize = 100,
                enablePlaceholders = false,
                initialLoadSize = 10
            ),
            pagingSourceFactory = { TVSearchDetailPagingSource(mediaService, query) }
        ).flow
    }


    fun getTvGenre(tv_genre: String): Flow<PagingData<TVshow>> {
        return Pager(
            config = PagingConfig(
                pageSize = 30,
                maxSize = 100,
                enablePlaceholders = false,
                initialLoadSize = 10
            ),
            pagingSourceFactory = { TvGenrePagingSource(mediaService, tv_genre) }
        ).flow
    }


    suspend fun getTVDetailData(id: Integer, language: String, api_key: String): TVDetail {

        return mediaService.getTVDetail(id, language, api_key)

    }

    fun getTVTrailer(id: Int): Flow<PagingData<Trailer>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false,
                initialLoadSize = 10,

                ),
            pagingSourceFactory = { TVTrailerPagingSource(mediaService, id) }
        ).flow
    }


    suspend fun getTVCreditData(tvId: Integer, api_key: String, language: String): Credit {
        return mediaService.getTVCredit(tvId, api_key, language)
    }


}