package com.quere.domain.usecase

import com.quere.domain.repository.search.SearchRepository
import javax.inject.Inject

class GetTVSearchRepoUseCase @Inject constructor(
    private val searchRepository: SearchRepository
) {

    suspend fun getTVSearch(query: String) = searchRepository.getTVSearch(query)
}