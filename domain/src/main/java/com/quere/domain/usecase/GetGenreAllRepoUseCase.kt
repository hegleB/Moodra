package com.quere.domain.usecase

import com.quere.domain.repository.common.CommonRepository
import javax.inject.Inject

class GetGenreAllRepoUseCase @Inject constructor(
    private val commonRepository: CommonRepository
){
    suspend fun getGenreAll(type:String,genre: String) = commonRepository.getGenreAll(type,genre)
}