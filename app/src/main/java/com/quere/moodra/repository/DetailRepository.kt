package com.quere.moodra.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.quere.moodra.paging.RecommendPagingSourcec
import com.quere.moodra.paging.SimilarPagingSource
import com.quere.moodra.retrofit.MediaService
import com.quere.moodra.retrofit.OtherContent
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DetailRepository @Inject constructor(
    val mediaService: MediaService
){

    fun getSimilar(id: Int): Flow<PagingData<OtherContent>> {
        return Pager(
            config = PagingConfig(
                pageSize = 30,
                maxSize = 100,
                enablePlaceholders = false,
                initialLoadSize = 10

            ),
            pagingSourceFactory = { SimilarPagingSource(mediaService, id) }
        ).flow
    }

    fun getRecommend(id: Int): Flow<PagingData<OtherContent>> {
        return Pager(
            config = PagingConfig(
                pageSize = 30,
                maxSize = 100,
                enablePlaceholders = false,
                initialLoadSize = 10

            ),
            pagingSourceFactory = { RecommendPagingSourcec(mediaService, id) }
        ).flow
    }
}