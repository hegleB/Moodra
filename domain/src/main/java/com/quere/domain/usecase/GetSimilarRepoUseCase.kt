package com.quere.domain.usecase

import com.quere.domain.repository.common.CommonRepository
import javax.inject.Inject

class GetSimilarRepoUseCase @Inject constructor(
    val commonRepository: CommonRepository
) {
    suspend fun getSimilar(type:String?,id: Int?) = commonRepository.getSimilar(type,id)

}