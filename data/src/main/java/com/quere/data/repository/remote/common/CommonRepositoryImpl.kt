package com.quere.data.repository.remote.common

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.quere.data.paging.common.*
import com.quere.data.repository.remote.movie.PAGE_SIZE
import com.quere.data.service.commonservice.CommonService
import com.quere.domain.model.common.*
import com.quere.domain.repository.common.CommonRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CommonRepositoryImpl @Inject constructor(
    private val commonService: CommonService
) : CommonRepository {


    override suspend fun getSimilar(type: String?, id: Int?): Flow<PagingData<OtherContent>> {
        return Pager(PagingConfig(PAGE_SIZE)) {
            SimilarPagingSource(commonService, type!!,id)
        }.flow
    }

    override suspend fun getRecommend(type: String?, id: Int?): Flow<PagingData<OtherContent>> {
        return Pager(PagingConfig(PAGE_SIZE)) {
            RecommendPagingSource(commonService, type!!,id)
        }.flow
    }

    override suspend fun getCredit(type: String?, id: Int?): Credit {
        return commonService.getCredit(type!!,id!!)
    }

    override suspend fun getTrailer(type: String?, id: Int?): Trailers {
        return commonService.getTrailer(type!!, id!!)
    }

    override suspend fun getGenre(type: String?, genre: String?): Flow<PagingData<Detail>> {
        return Pager(PagingConfig(PAGE_SIZE)) {
            GenrePagingSource(commonService, type!!,genre!!)
        }.flow
    }

    override suspend fun getMoviePopular(): Flow<PagingData<Detail>> {
        return Pager(PagingConfig(PAGE_SIZE)) {
            GenreViewPagerPagingSource(commonService, "movie")
        }.flow
    }

    override suspend fun getTvPopular(): Flow<PagingData<Detail>> {
        return Pager(PagingConfig(PAGE_SIZE)) {
            GenreViewPagerPagingSource(commonService, "tv")
        }.flow
    }

    override suspend fun getGenreAll(type: String, genre: String): Flow<PagingData<Detail>> {
       return Pager(PagingConfig(PAGE_SIZE)) {
           GenreAllPagingSource(commonService, type,genre)
       }.flow
    }
}