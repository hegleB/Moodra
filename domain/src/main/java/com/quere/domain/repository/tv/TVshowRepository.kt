package com.quere.domain.repository.tv

import androidx.paging.PagingData
import com.quere.domain.model.tv.TVshow
import kotlinx.coroutines.flow.Flow

interface TVshowRepository {

    suspend fun getTvshow(type: String?) : Flow<PagingData<TVshow>>
    suspend fun getTvDetail(id:Int?) : TVshow?

}