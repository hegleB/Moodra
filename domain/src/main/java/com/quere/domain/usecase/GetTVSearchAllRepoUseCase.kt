package com.quere.domain.usecase

import com.quere.domain.repository.search.SearchRepository
import javax.inject.Inject

class GetTVSearchAllRepoUseCase @Inject constructor(
    private val searchRepository: SearchRepository
) {

    suspend fun getTVSearchAll(query: String) = searchRepository.getTVSearchAll(query)
}