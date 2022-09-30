package com.quere.domain.usecase

import com.quere.domain.repository.common.CommonRepository
import javax.inject.Inject

class GetTvPopularRepoUseCase @Inject constructor(
    private val commonRepository: CommonRepository
){
    suspend fun getTvPopular() = commonRepository.getTvPopular()
}