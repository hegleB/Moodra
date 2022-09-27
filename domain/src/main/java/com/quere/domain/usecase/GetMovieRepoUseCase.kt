package com.quere.domain.usecase

import com.quere.domain.repository.movie.MovieRepository
import javax.inject.Inject

class GetMovieRepoUseCase @Inject constructor(
    private val movieRepository: MovieRepository
){

    suspend fun getMovie(type: String?) = movieRepository.getMovie(type)

}