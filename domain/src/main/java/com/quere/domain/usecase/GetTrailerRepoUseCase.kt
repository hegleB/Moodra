package com.quere.domain.usecase

import com.quere.domain.repository.common.CommonRepository

import javax.inject.Inject

class GetTrailerRepoUseCase @Inject constructor(
    val commonRepository: CommonRepository
) {
    suspend fun getTrailer(type:String? ,id: Int?) = commonRepository.getTrailer(type,id)

}