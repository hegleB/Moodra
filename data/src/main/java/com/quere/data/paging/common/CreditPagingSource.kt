package com.quere.data.paging.common

import com.quere.data.base.BasePagingSource
import com.quere.data.service.commonservice.CommonService
import com.quere.domain.model.common.Actor

class CreditPagingSource(
    private val service: CommonService,
    private val type:String,
    private val id : Int?

) : BasePagingSource<Actor>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Actor> {

        dataList = service.getCredit(type,id!!).cast

        return super.load(params)
    }
}