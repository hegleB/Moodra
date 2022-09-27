package com.quere.domain.usecase

import com.quere.domain.repository.tv.TVshowRepository
import javax.inject.Inject

class GetTVshowRepoUseCase @Inject constructor(
    private val tVshowRepository: TVshowRepository
) {

    suspend fun getTvshow(type: String?) = tVshowRepository.getTvshow(type)
}