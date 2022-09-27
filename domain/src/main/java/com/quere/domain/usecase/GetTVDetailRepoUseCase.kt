package com.quere.domain.usecase

import com.quere.domain.repository.tv.TVshowRepository
import javax.inject.Inject

class GetTVDetailRepoUseCase @Inject constructor(
    private val tVshowRepository: TVshowRepository
){
    suspend fun getTVDetail(id: Int) = tVshowRepository.getTvDetail(id)
}