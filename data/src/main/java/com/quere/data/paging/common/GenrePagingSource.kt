package com.quere.data.paging.common

import com.quere.data.base.BasePagingSource
import com.quere.data.service.commonservice.CommonService
import com.quere.domain.model.common.Detail


class GenrePagingSource(
    private val service: CommonService,
    private val type:String,
    private val genre:String


) : BasePagingSource<Detail>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Detail> {

        dataList = service.getGenre(type,genre,1).results
        println("Genre : " + dataList)

        return super.load(params)
    }
}