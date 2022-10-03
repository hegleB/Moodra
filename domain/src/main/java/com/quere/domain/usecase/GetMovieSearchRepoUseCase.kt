package com.quere.domain.usecase

import com.quere.domain.repository.search.SearchRepository
import javax.inject.Inject

class GetMovieSearchRepoUseCase @Inject constructor(
    private val searchRepository: SearchRepository
) {

    suspend fun getMovieSearch(query: String) = searchRepository.getMovieSearch(query)
}