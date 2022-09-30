package com.quere.domain.usecase

import com.quere.domain.repository.common.CommonRepository
import javax.inject.Inject

class GetMoviePopularRepoUseCase @Inject constructor(
    private val commonRepository: CommonRepository
){
    suspend fun getMoviePopular() = commonRepository.getMoviePopular()
}