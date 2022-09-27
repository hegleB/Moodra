package com.quere.data.paging.common

import com.quere.data.base.BasePagingSource
import com.quere.data.service.commonservice.CommonService
import com.quere.domain.model.common.Detail

class GenreViewPagerPagingSource(
    private val service: CommonService,
    private val type:String

) : BasePagingSource<Detail>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Detail> {

        dataList = service.getGenrePopular(type).results


        return super.load(params)
    }
}