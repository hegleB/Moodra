package com.quere.domain.usecase

import com.quere.domain.repository.common.CommonRepository
import javax.inject.Inject

class GetRecommendRepoUseCase @Inject constructor(
    private val commonRepository: CommonRepository
){
    suspend fun getRecommend(type:String?,id: Int?) = commonRepository.getRecommend(type,id)
}