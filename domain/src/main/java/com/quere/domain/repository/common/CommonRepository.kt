package com.quere.domain.repository.common

import androidx.paging.PagingData
import com.quere.domain.model.common.Credit
import com.quere.domain.model.common.Detail
import com.quere.domain.model.common.OtherContent
import com.quere.domain.model.common.Trailers
import kotlinx.coroutines.flow.Flow

interface CommonRepository {

    suspend fun getSimilar(type:String?,id :Int?) : Flow<PagingData<OtherContent>>?
    suspend fun getRecommend(type:String?,id :Int?) : Flow<PagingData<OtherContent>>?
    suspend fun getCredit(type:String?,id :Int?) : Credit?
    suspend fun getTrailer(type:String?,id :Int?) : Trailers?
    suspend fun getGenre(type:String?,genre : String?) : Flow<PagingData<Detail>>?
    suspend fun getMoviePopular() : Flow<PagingData<Detail>>?
    suspend fun getTvPopular() : Flow<PagingData<Detail>>?
    suspend fun getGenreAll(type:String?,genre : String?) : Flow<PagingData<Detail>>?

}