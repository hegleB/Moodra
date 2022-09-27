package com.quere.domain.usecase

import com.quere.domain.repository.common.CommonRepository
import javax.inject.Inject

class GetGenreRepoUseCase @Inject constructor(
    private val commonRepository: CommonRepository
){
    suspend fun getGenre(type:String,genre: String) = commonRepository.getGenre(type,genre)
}