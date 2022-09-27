package com.quere.data.paging.common

import com.quere.data.base.BasePagingSource
import com.quere.data.service.commonservice.CommonService
import com.quere.domain.model.common.OtherContent

class RecommendPagingSource(
    private val service: CommonService,
    private val type:String,
    private val id : Int?


) : BasePagingSource<OtherContent>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, OtherContent> {

        dataList = service.getRecommendation(type,id!!,1).results

        return super.load(params)
    }
}