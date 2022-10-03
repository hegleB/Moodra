package com.quere.domain.usecase

import com.quere.domain.repository.search.SearchRepository
import javax.inject.Inject

class GetMovieSearchAllRepoUseCase @Inject constructor(
    private val searchRepository: SearchRepository
) {

    suspend fun getMovieSearchAll(query: String) = searchRepository.getMovieSearchAll(query)
}