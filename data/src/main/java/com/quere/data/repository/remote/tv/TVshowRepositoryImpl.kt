package com.quere.data.repository.remote.tv

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.quere.data.paging.tv.TVPagingSource
import com.quere.data.repository.remote.movie.PAGE_SIZE
import com.quere.data.service.tvservice.TVService
import com.quere.domain.model.tv.TVshow
import com.quere.domain.repository.tv.TVshowRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TVshowRepositoryImpl @Inject constructor(
    private val tvService: TVService
) : TVshowRepository {

    override suspend fun getTvshow(type: String?): Flow<PagingData<TVshow>> {
        return Pager(PagingConfig(PAGE_SIZE)) {
            TVPagingSource(tvService, type!!)
        }.flow
    }

    override suspend fun getTvDetail(id: Int?): TVshow {
        return tvService.getTVDetail(id?:0)
    }
}