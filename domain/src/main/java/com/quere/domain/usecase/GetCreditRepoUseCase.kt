package com.quere.domain.usecase

import com.quere.domain.repository.common.CommonRepository
import javax.inject.Inject

class GetCreditRepoUseCase @Inject constructor(
    private val commonRepository: CommonRepository
){
    suspend fun getCredit(type:String,id: Int) = commonRepository.getCredit(type,id)
}