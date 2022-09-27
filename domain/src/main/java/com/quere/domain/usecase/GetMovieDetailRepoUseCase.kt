package com.quere.domain.usecase

import com.quere.domain.repository.movie.MovieRepository
import javax.inject.Inject

class GetMovieDetailRepoUseCase @Inject constructor(
    private val movieRepository: MovieRepository
){
    suspend fun getMovieDetail(id: Int) = movieRepository.getMovieDetail(id)
}