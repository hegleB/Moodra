package com.quere.domain.usecase

import com.quere.domain.repository.common.CommonRepository
import javax.inject.Inject

class GetGenrePopularRepoUseCase @Inject constructor(
    private val commonRepository: CommonRepository
){
    suspend fun getGenrePopular(type:String) = commonRepository.getGenrePopular(type)
}