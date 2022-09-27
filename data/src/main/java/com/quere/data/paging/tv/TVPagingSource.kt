package com.quere.data.paging.tv

import com.quere.data.base.BasePagingSource
import com.quere.data.service.tvservice.TVService
import com.quere.domain.model.tv.TVshow

class TVPagingSource(
    private val service: TVService,
    private val type : String?

) : BasePagingSource<TVshow>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TVshow> {

        dataList = service.getTV(type!!).results

        return super.load(params)
    }
}